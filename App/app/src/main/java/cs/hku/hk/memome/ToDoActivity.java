package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import cs.hku.hk.memome.ui.MyListViewAdapter;
import cs.hku.hk.memome.ui.todo.ToDoViewModel;

public class ToDoActivity extends AppCompatActivity implements MyListViewAdapter.ItemClickListener
{
    private Toolbar upperToolBar;
    private ToDoViewModel toDoViewModel;
    private ListView listView;
    private MyListViewAdapter listAdapter;
    boolean [] loadedResult;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        toDoViewModel = new ToDoViewModel();

        Bundle extras = getIntent().getExtras();
        String title;
        title = extras.getString("title");

        listView = findViewById(R.id.todo_listview);
        FloatingActionButton addTodo = findViewById(R.id.add_todo);
        String[] details = toDoViewModel.getListDetails(title);

        loadedResult = toDoViewModel.loadTaskResuls(title);

        listAdapter = new MyListViewAdapter(this, details, loadedResult);
        listAdapter.setClickListener(this);

        listView.setAdapter(listAdapter);

        upperToolBar = findViewById(R.id.toolbar_todo);
        upperToolBar.setTitle(title);
        upperToolBar.setNavigationIcon(R.drawable.ic_return_home_24dp);
        upperToolBar.setNavigationContentDescription(R.string.return_home);
        setSupportActionBar(upperToolBar);
        upperToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("ActivityDebug", "Navigation on click event");
                Intent returnHome = new Intent();
                setResult(RESULT_OK, returnHome);
                finish();
            }
        });

        addTodo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: store a new todo to DB, assign an unique ID
                Toast.makeText(ToDoActivity.this,"add a todo", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position)
    {
        //TODO: What should happend after being checked (or not?)
        Toast.makeText(view.getContext(), "Clicked at "+position, Toast.LENGTH_SHORT).show();
    }
}
