package cs.hku.hk.memome;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity
{
    private Toolbar upperToolBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_profile);

        upperToolBar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(upperToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
