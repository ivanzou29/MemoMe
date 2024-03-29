package cs.hku.hk.memome.ui.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import cs.hku.hk.memome.ui.ProcessingDialog;
import cs.hku.hk.memome.uiAdapter.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;
import cs.hku.hk.memome.ToDoActivity;

/**
 * Fragment for the to do list. Implementing infinite scrolling, and scrolling down to refresh.
 */
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
    private EditText newList;

    private String email;
    private ProcessingDialog processing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences sp = this.getActivity().getSharedPreferences("config", 0);
        email = sp.getString("email", "");
        toDoViewModel =
                ViewModelProviders.of(this).get(ToDoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        FloatingActionButton add = root.findViewById(R.id.floatingActionButton_plus);
        newList = root.findViewById(R.id.newList);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = newList.getText().toString();
                if(title.equals("")){
                    Snackbar.make(view, "Please enter a list name before click", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    newList.getText().clear();
                    Snackbar.make(view, "To add a new list " + title, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent =  new Intent(view.getContext(), ToDoActivity.class);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }
            }
        });
        toDoViewModel.setEmail(email);

        allTitles = new ArrayList<>(toDoViewModel.getMyData());
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
                allTitles.addAll(toDoViewModel.getMyData());
                toDoAdapter.notifyDataSetChanged();
                processing.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(this.getContext(), "You clicked data " + toDoAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
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
        allTitles.addAll(toDoViewModel.getMyData());
        toDoAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}