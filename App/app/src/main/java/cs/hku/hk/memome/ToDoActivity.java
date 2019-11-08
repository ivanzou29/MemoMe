package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import cs.hku.hk.memome.ui.todo.ToDoViewModel;

public class ToDoActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    private ToDoViewModel toDoViewModel;
    private ListView listView;
    private ArrayAdapter<String> listAdapter ;
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
        String[] details = toDoViewModel.getListDetails(title);
        ArrayList<String> detailsList = new ArrayList<String>();
        detailsList.addAll(Arrays.asList(details) );
        listAdapter = new ArrayAdapter<String>(this, R.layout.list_details, detailsList);
        listView.setAdapter(listAdapter);

        upperToolBar = findViewById(R.id.toolbar_todo);
        upperToolBar.setTitle(title);
        upperToolBar.setNavigationIcon(R.drawable.ic_return_home_24dp);
        upperToolBar.setNavigationContentDescription(R.string.return_home);
        setSupportActionBar(upperToolBar);
        upperToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ActivityDebug", "Navigation on click event");
                Intent returnHome = new Intent();
                setResult(RESULT_OK, returnHome);
                finish();
            }
        });

    }
}
