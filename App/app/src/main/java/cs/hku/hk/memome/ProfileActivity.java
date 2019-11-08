package cs.hku.hk.memome;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        upperToolBar = findViewById(R.id.toolbar_profile);
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
    }
}
