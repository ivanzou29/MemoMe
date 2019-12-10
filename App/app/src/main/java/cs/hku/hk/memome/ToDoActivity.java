package cs.hku.hk.memome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Date;

import cs.hku.hk.memome.jdbc.ComposeJdbcDao;
import cs.hku.hk.memome.jdbc.OwnJdbcDao;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.jdbc.TaskJdbcDao;
import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.Own;
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
    private String[] details;
    private boolean[] loadedResult;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        SharedPreferences sp = getSharedPreferences("config", 0);
        email = sp.getString("email", "");

        toDoViewModel = new ToDoViewModel();
        toDoViewModel.setEmail(email);

        final Bundle extras = getIntent().getExtras();

        title = extras.getString("title");

        listView = findViewById(R.id.todo_listview);
        FloatingActionButton addTodo = findViewById(R.id.add_todo);
        FloatingActionButton deleteTodo = findViewById(R.id.delete_todo);

        details = toDoViewModel.getListDetails(title);
        loadedResult = toDoViewModel.loadTaskResults(title);

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
                EditText newtodo = findViewById(R.id.newTodo);
                String taskContent = newtodo.getText().toString();
                if(!taskContent.equals("")){
                    Task task = new Task();
                    task.setTaskName(taskContent);
                    task.setListName(title);
                    task.setEmail(email);
                    task.setFinished(false);
                    task.setDeadline(new Date(System.currentTimeMillis()));

                    TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
                    taskJdbcDao.insertTask(task);
                    Toast.makeText(ToDoActivity.this,"Added a new task", Toast.LENGTH_LONG).show();

                    newtodo.getText().clear();
                    reloadTasks();
                }else {
                    Toast.makeText(ToDoActivity.this,"Enter a valid task", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteTodo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder( ToDoActivity.this )
                        .setTitle( "Confirmation" )
                        .setMessage( "Are you sure you want to delete this To-do list?" )
                        .setNegativeButton( "Cancel",null )
                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
                                    taskJdbcDao.deleteList(email, title);
                                    Toast.makeText(ToDoActivity.this, "This list is deleted.", Toast.LENGTH_SHORT).show();
                                    Intent returnHome = new Intent();
                                    setResult(RESULT_OK, returnHome);
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(ToDoActivity.this, "Deletion failed due to server problem.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } )
                        .show();
            }
        });

    }

    private void reloadTasks() {
        details = toDoViewModel.getListDetails(title);
        loadedResult = toDoViewModel.loadTaskResults(title);

        listAdapter = new TodoListViewAdapter(this, details, loadedResult);
        listAdapter.setClickListener(this);

        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        if(position >= listAdapter.getCount() || viewModel==null)
            return;
        boolean newValue = !listAdapter.getItem(position).isChecked();

        listAdapter.getItem(position).setChecked(newValue);
        listAdapter.notifyDataSetChanged();
        TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
        Bundle extras = getIntent().getExtras();

        String listName = extras.getString("title");
        String taskName = listAdapter.getItem(position).getText();

        taskJdbcDao.finishTask(email, listName, taskName);

        viewModel.updateCompletion(position, newValue);
        if(0==viewModel.getRemained() && newValue){

            Toast.makeText(view.getContext(),"Congratulations! You have finished the to-do list and you get 10 coins!", Toast.LENGTH_SHORT).show();
            UserJdbcDao userJdbcDao =new UserJdbcDao();
            userJdbcDao.updateCoinByEmailAndQuantity(email,10);
        }
        else if(newValue)
            Toast.makeText(view.getContext(),"Wow! You have finished the task!", Toast.LENGTH_SHORT).show();
    }
}
