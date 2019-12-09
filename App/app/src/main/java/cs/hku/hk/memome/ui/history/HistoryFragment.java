package cs.hku.hk.memome.ui.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import cs.hku.hk.memome.DiaryActivity;
import cs.hku.hk.memome.ui.ProcessingDialog;
import cs.hku.hk.memome.uiAdapter.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;

/**
 * Fragment for the history. Implementing infinite scrolling, and scrolling down to refresh.
 */
public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.ItemClickListener{

    private HistoryViewModel historyViewModel;
        private MyRecyclerViewAdapter historyAdapter;

    private List<String> allTitles;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int state;
    private int lastVisibleItemPosition;
    private int offset;
    private int moveY;
    private int oldY;

    private String email;

    private ProcessingDialog processing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences sp = this.getActivity().getSharedPreferences("config", 0);
        email = sp.getString("email", "");
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        historyViewModel.setEmail(email);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        allTitles = new ArrayList<String>(historyViewModel.getTitles());
        recyclerView = root.findViewById(R.id.rvDiaries);
        int numberOfColumns = 1;

        layoutManager = new GridLayoutManager(this.getContext(), numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter = new MyRecyclerViewAdapter(this.getContext(), allTitles);
        historyAdapter.setClickListener(this);
        recyclerView.setAdapter(historyAdapter);

        enableScrollingLoad();

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_history);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                //reloadEntireContent();
            }
        });

        processing = new ProcessingDialog(root);
        processing.show();

        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getView().post(new Runnable()
        {
            @Override
            public void run()
            {
                allTitles.clear();
                allTitles.addAll(historyViewModel.getTitles());
                historyAdapter.notifyDataSetChanged();
                processing.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this.getContext(), "You clicked data " + historyAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(view.getContext(), DiaryActivity.class);
        intent.putExtra("title", historyAdapter.getItem(position));
        intent.putExtra("content", historyViewModel.getContents(historyAdapter.getItem(position)));
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
        allTitles.clear();
        allTitles.addAll(historyViewModel.getTitles());
        historyAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}