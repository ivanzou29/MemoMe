package cs.hku.hk.memome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cs.hku.hk.memome.jdbc.TaskJdbcDao;
import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.Task;
import cs.hku.hk.memome.uiAdapter.TodoListViewAdapter;
import cs.hku.hk.memome.ui.todo.ToDoViewModel;

/**
 * This is the activity for the details of each To Do item. The To Do item is indexing by its title
 * (i.e. the date) and retrieved from the instance of ToDoViewModel. The status are manipulated by
 * one instance of ToDoActivityViewModel.
 */
public class ToDoActivity extends AppCompatActivity implements TodoListViewAdapter.ItemClickListener
{
    private Toolbar upperToolBar;
    private ToDoViewModel toDoViewModel;
    private ListView listView;
    private TodoListViewAdapter listAdapter;
    private ToDoActivityViewModel viewModel;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        SharedPreferences sp = getSharedPreferences("config", 0);
        email = sp.getString("email", "");

        toDoViewModel = new ToDoViewModel();
        toDoViewModel.getMyData();//TODO: overload the getMyData so that will only load one item
                                  //efficiency concerns

        final Bundle extras = getIntent().getExtras();
        final String title;
        title = extras.getString("title");

        listView = findViewById(R.id.todo_listview);
        FloatingActionButton addTodo = findViewById(R.id.add_todo);

        String[] details = toDoViewModel.getListDetails(title);
        boolean [] loadedResult = toDoViewModel.loadTaskResuls(title);

        viewModel = new ToDoActivityViewModel(title, details, loadedResult);

        listAdapter = new TodoListViewAdapter(this, details, loadedResult);
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
                EditText newtodo = findViewById(R.id.newTodo);
                String taskContent = newtodo.getText().toString();
                String email = extras.getString("email");
                Task task = new Task();
                task.setTaskName(taskContent);
                task.setListName(title);
                task.setEmail(email);

                TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
                taskJdbcDao.insertTask(task);
                Toast.makeText(ToDoActivity.this,"add a todo", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position)
    {
        if(position >= listAdapter.getCount() || viewModel==null)
            return;
        boolean newValue = !listAdapter.getItem(position).isChecked();

        listAdapter.getItem(position).setChecked(newValue);
        listAdapter.notifyDataSetChanged();
        //TODO: send to server for this clicking
        TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");// to fragment 没有保存
        String taskname = extras.getString("title");

        //taskJdbcDao.finishTask(email, taskname); 不知道在哪看listname

        viewModel.updateCompletion(position, newValue);
        if(0==viewModel.getRemained() && newValue){

            Toast.makeText(view.getContext(),"All are done! Congratulations", Toast.LENGTH_SHORT).show();
            UserJdbcDao userJdbcDao =new UserJdbcDao();
            userJdbcDao.updateCoinByEmailAndQuantity(email,1);
        }
        else if(newValue)
            Toast.makeText(view.getContext(),"Wow!", Toast.LENGTH_SHORT).show();
    }
}
