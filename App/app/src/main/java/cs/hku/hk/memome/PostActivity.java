package cs.hku.hk.memome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.model.Post;
import cs.hku.hk.memome.ui.history.HistoryViewModel;

/**
 * The activity for the details of each post. Currently, the content are retrieved via intent. This
 * works for the small post.
 */
public class PostActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    private String title;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        SharedPreferences sp = getSharedPreferences("config", 0);
        email = sp.getString("email", "");

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
                PostJdbcDao postJdbcDao = new PostJdbcDao();
                Post post= postJdbcDao.getPostByPostTitle(title);
                int like = post.getLike() + 1;
                postJdbcDao.updateLikeByTitle(like, title);

                Toast.makeText(v.getContext(), "Thank you for loving " + title, Toast.LENGTH_SHORT).show();
            }
        });

        Button repo = findViewById(R.id.repo_post);
        repo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "We have received your report of the post " + title + ".", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
