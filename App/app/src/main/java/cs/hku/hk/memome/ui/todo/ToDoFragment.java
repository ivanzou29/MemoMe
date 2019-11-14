package cs.hku.hk.memome.ui.todo;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cs.hku.hk.memome.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;
import cs.hku.hk.memome.ToDoActivity;


public class ToDoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.ItemClickListener{

    private ToDoViewModel toDoViewModel;
    private MyRecyclerViewAdapter toDoAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int state;
    private int lastVisibleItemPosition;
    private int offset;
    private int moveY;
    private int oldY;

    private List<String> allTitles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        toDoViewModel =
                ViewModelProviders.of(this).get(ToDoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "To add a new list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        allTitles = toDoViewModel.getMyData();
        recyclerView = root.findViewById(R.id.rvNumbers);
        int numberOfColumns = 1;
        layoutManager = new GridLayoutManager(this.getContext(), numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        toDoAdapter = new MyRecyclerViewAdapter(this.getContext(), allTitles);
        toDoAdapter.setClickListener(this);
        recyclerView.setAdapter(toDoAdapter);

        enableScrollingLoad();

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_todo);
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
    public void onItemClick(View view, int position) {
        Toast.makeText(this.getContext(), "You clicked data " + toDoAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(view.getContext(), ToDoActivity.class);
        intent.putExtra("title", toDoAdapter.getItem(position));
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
                        if((1==state || 2==state) && lastVisibleItemPosition == toDoAdapter.getItemCount()-1)
                        {
                            if(offset>0 || (0==offset && moveY<0))
                            //offset > 0 <=> scrolling upwards
                            //offset == 0 <=> no scrolling, i.e. less than
                            {
                                Toast.makeText(v.getContext(),R.string.loading_new_items,Toast.LENGTH_SHORT).show();
                                int originalSize = allTitles.size();
                                allTitles = toDoViewModel.getNewData();
                                toDoAdapter.notifyItemInserted(originalSize);
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
        allTitles = toDoViewModel.getMyData();
        toDoAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}