package cs.hku.hk.memome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cs.hku.hk.memome.jdbc.ComposeJdbcDao;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.jdbc.TaskJdbcDao;
import cs.hku.hk.memome.model.Compose;
import cs.hku.hk.memome.model.Post;
import cs.hku.hk.memome.ui.history.HistoryViewModel;

/**
 * The activity for the Diary details
 */
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
        final String title;
        title = extras.getString("title");
        String content = extras.getString("content");

        historyViewModel = new HistoryViewModel();

        upperToolBar = findViewById(R.id.toolbar_diary);
        upperToolBar.setTitle(title.substring(0, title.length()-24));
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

        Button delete = findViewById(R.id.delete_post);
        delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DiaryActivity.this )
                        .setTitle( "Confirmation" )
                        .setMessage( "Are you sure you want to delete this post?" )
                        .setNegativeButton( "Cancel",null )
                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    PostJdbcDao postJdbcDao = new PostJdbcDao();
                                    Post post = postJdbcDao.getPostByPostId(title);
                                    if(post != null){
                                        postJdbcDao.deletePost(post.getPostId());
                                    }

                                    ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                                    try {
                                        composeJdbcDao.deleteCompose(title);
                                        Toast.makeText(DiaryActivity.this, "This post is deleted.", Toast.LENGTH_SHORT).show();
                                        Log.d("ActivityDebug","delete posts");
                                        Intent returnHome = new Intent();
                                        setResult(RESULT_OK, returnHome);
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(DiaryActivity.this, "Deletion failed due to server problem.", Toast.LENGTH_SHORT).show();
                                    } } catch (Exception e) {
                                    Toast.makeText(DiaryActivity.this, "Deletion failed due to server problem.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } )
                        .show();
            }
        });
    }
}
