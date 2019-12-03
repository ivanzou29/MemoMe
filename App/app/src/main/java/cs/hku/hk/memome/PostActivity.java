package cs.hku.hk.memome;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cs.hku.hk.memome.jdbc.ComposeJdbcDao;
import cs.hku.hk.memome.jdbc.OwnJdbcDao;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.Own;
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

        Button gift = findViewById(R.id.favo_post);
        gift.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PostActivity.this,v);
                popupMenu.inflate(R.menu.gift_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.gift_flower:
                                new AlertDialog.Builder( PostActivity.this )
                                        .setTitle( "Confirmation" )
                                        .setMessage( "Are you sure you want to send a flower to this post, it will cost your 2 coins" )
                                        .setNegativeButton( "Cancel",null )
                                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                UserJdbcDao userJdbcDao = new UserJdbcDao();

                                                int currentCoins = userJdbcDao.getCoinsByEmail(email);
                                                if (currentCoins < 2) {
                                                    Toast.makeText(PostActivity.this, "You do not have enough coins!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    userJdbcDao.updateCoinByEmailAndQuantity(email, -2);
                                                    PostJdbcDao postJdbcDao = new PostJdbcDao();
                                                    ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                                                    OwnJdbcDao ownJdbcDao = new OwnJdbcDao();
                                                    postJdbcDao.increaseLikeByTitle(2, title);
                                                    String composerEmail = composeJdbcDao.getUserEmailByPostId(title);
                                                    Own increaseOwn = new Own(composerEmail, "Flower", 1);
                                                    ownJdbcDao.increaseGiftOwnership(increaseOwn);


                                                    Toast.makeText(PostActivity.this, "thank you for your flower", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } )
                                        .show();
                                break;
                            case R.id.gift_cake:
                                new AlertDialog.Builder( PostActivity.this )
                                        .setTitle( "Confirmation" )
                                        .setMessage( "Are you sure you want to send a cake to this post, it will cost your 3 coins" )
                                        .setNegativeButton( "Cancel",null )
                                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                UserJdbcDao userJdbcDao = new UserJdbcDao();

                                                int currentCoins = userJdbcDao.getCoinsByEmail(email);
                                                if (currentCoins < 3) {
                                                    Toast.makeText(PostActivity.this, "You do not have enough coins!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    userJdbcDao.updateCoinByEmailAndQuantity(email, -3);
                                                    PostJdbcDao postJdbcDao = new PostJdbcDao();
                                                    ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                                                    OwnJdbcDao ownJdbcDao = new OwnJdbcDao();
                                                    postJdbcDao.increaseLikeByTitle(3, title);
                                                    String composerEmail = composeJdbcDao.getUserEmailByPostId(title);
                                                    Own increaseOwn = new Own(composerEmail, "Cake", 1);
                                                    ownJdbcDao.increaseGiftOwnership(increaseOwn);


                                                    Toast.makeText(PostActivity.this, "thank you for your cake", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } )
                                        .show();
                                break;
                            case R.id.gift_teddy:
                                new AlertDialog.Builder( PostActivity.this )
                                        .setTitle( "Confirmation" )
                                        .setMessage( "Are you sure you want to send a Teddy bear to this post, it will cost your 4 coins" )
                                        .setNegativeButton( "Cancel",null )
                                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                UserJdbcDao userJdbcDao = new UserJdbcDao();

                                                int currentCoins = userJdbcDao.getCoinsByEmail(email);
                                                if (currentCoins < 4) {
                                                    Toast.makeText(PostActivity.this, "You do not have enough coins!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    userJdbcDao.updateCoinByEmailAndQuantity(email, -4);
                                                    PostJdbcDao postJdbcDao = new PostJdbcDao();
                                                    ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                                                    OwnJdbcDao ownJdbcDao = new OwnJdbcDao();
                                                    postJdbcDao.increaseLikeByTitle(4, title);
                                                    String composerEmail = composeJdbcDao.getUserEmailByPostId(title);
                                                    Own increaseOwn = new Own(composerEmail, "Teddy Bear", 1);
                                                    ownJdbcDao.increaseGiftOwnership(increaseOwn);


                                                    Toast.makeText(PostActivity.this, "thank you for your teddy bear", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } )
                                        .show();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        Button repo = findViewById(R.id.repo_post);
        repo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder( PostActivity.this )
                        .setTitle( "Confirmation" )
                        .setMessage( "Are you sure you want to report " + title + "?" )
                        .setNegativeButton( "Cancel",null )
                        .setPositiveButton( "Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PostActivity.this,"You have reported " + title,Toast.LENGTH_SHORT).show();
                            }
                        } )
                        .show();
            }
        });
    }
}
