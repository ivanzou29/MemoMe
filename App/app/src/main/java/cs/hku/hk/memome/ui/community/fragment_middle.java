package cs.hku.hk.memome.ui.community;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cs.hku.hk.memome.DiaryActivity;
import cs.hku.hk.memome.PostActivity;
import cs.hku.hk.memome.uiAdapter.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;

public class fragment_middle extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.ItemClickListener
{
    private CommunityViewModel communityViewModel;
    private MyRecyclerViewAdapter communityAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    static final private int NUM_COLUMN = 1;

    private List<String> allTitles;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private int state;
    private int lastVisibleItemPosition;
    private int offset;
    private int moveY;
    private int oldY;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);
        allTitles = communityViewModel.getTitles(CommunityViewModel.MIDDEL_TAB);

        View root = inflater.inflate(R.layout.fagment_community_middle, container, false);

        recyclerView = root.findViewById(R.id.rvMiddleDiaries);
        layoutManager = new GridLayoutManager(this.getContext(), NUM_COLUMN);
        recyclerView.setLayoutManager(layoutManager);

        communityAdapter = new MyRecyclerViewAdapter(this.getContext(), allTitles);
        communityAdapter.setClickListener(this);
        recyclerView.setAdapter(communityAdapter);

        enableScrollingLoad();

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_middle);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                reloadEntireContent();
            }
        });
        return root;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Toast.makeText(this.getContext(), "You clicked data " + communityAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(view.getContext(), PostActivity.class);
        intent.putExtra("title", communityAdapter.getItem(position));
        intent.putExtra("content",communityViewModel.getContents(CommunityViewModel.MIDDEL_TAB,communityAdapter.getItem(position)));
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void enableScrollingLoad()
    {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                offset = dy;
            }
        });

        this.recyclerView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        moveY = (int)event.getY() - oldY;
                        oldY = (int)event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if((1==state || 2==state) && lastVisibleItemPosition == communityAdapter.getItemCount()-1)
                        {
                            if(offset>0 || (0==offset && moveY<0))
                            //offset > 0 <=> scrolling upwards
                            //offset == 0 <=> no scrolling, i.e. less than
                            {
                                Toast.makeText(v.getContext(),R.string.loading_new_items,Toast.LENGTH_SHORT).show();
                                int originalSize = allTitles.size();
                                allTitles = communityViewModel.getNewData(CommunityViewModel.MIDDEL_TAB);
                                communityAdapter.notifyItemInserted(originalSize);
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public void onRefresh()
    {
        reloadEntireContent();
    }


    private void reloadEntireContent()
    {
        swipeRefreshLayout.setRefreshing(true);
        allTitles = communityViewModel.getTitles(CommunityViewModel.MIDDEL_TAB);
        communityAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}
