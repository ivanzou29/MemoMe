package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cs.hku.hk.memome.ui.history.HistoryViewModel;
import cs.hku.hk.memome.ui.todo.ToDoViewModel;

public class DiaryActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    private HistoryViewModel historyViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Bundle extras = getIntent().getExtras();
        String title;
        title = extras.getString("title");
        String content = extras.getString("content");

        historyViewModel = new HistoryViewModel();

        upperToolBar = findViewById(R.id.toolbar_diary);
        upperToolBar.setTitle(title);
        upperToolBar.setNavigationIcon(R.drawable.ic_return_home_24dp);
        upperToolBar.setNavigationContentDescription(R.string.return_home);
        setSupportActionBar(upperToolBar);
        upperToolBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("ActivityDebug","Navigation on click event");
                Intent returnHome = new Intent();
                setResult(RESULT_OK, returnHome);
                finish();
            }
        });

        TextView diary =findViewById(R.id.diary);
        diary.setText(content);
    }
}
