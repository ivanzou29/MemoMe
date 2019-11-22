package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs.hku.hk.memome.ui.history.HistoryViewModel;

public class PostActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Bundle extras = getIntent().getExtras();
        title = extras.getString("title");
        String content = extras.getString("content");

        upperToolBar = findViewById(R.id.toolbar_post);
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

        Button favo = findViewById(R.id.favo_post);
        favo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TODO: increase favo number in DB
                Toast.makeText(v.getContext(), "thank you for loving " + title, Toast.LENGTH_SHORT).show();
            }
        });

        Button repo = findViewById(R.id.repo_post);
        repo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //TODO: report this post
                Toast.makeText(v.getContext(), "Are you sure you wanna report " + title + "?", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
