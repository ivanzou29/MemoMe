package cs.hku.hk.memome.ui.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import cs.hku.hk.memome.MainActivity;
import cs.hku.hk.memome.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;
import cs.hku.hk.memome.ToDoActivity;

public class ToDoFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    private ToDoViewModel toDoViewModel;
    MyRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoViewModel =
                ViewModelProviders.of(this).get(ToDoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        FloatingActionButton fab = root.findViewById(R.id.floatingActionButton_plus);
//        TextView listname = root.findViewById(R.id.list_name);
//        if (data.length == 0) {
//            listname.setText("no to-do list yet");
//        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "To add a new list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String[] data = toDoViewModel.getMyData();
        RecyclerView recyclerView = root.findViewById(R.id.rvNumbers);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this.getContext(), data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this.getContext(), "You clicked data " + adapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(view.getContext(), ToDoActivity.class);
        intent.putExtra("title", adapter.getItem(position));
        startActivity(intent);
    }
}