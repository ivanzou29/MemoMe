package cs.hku.hk.memome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs.hku.hk.memome.uiAdapter.ProfileListViewAdapter;

public class ProfileActivity extends AppCompatActivity implements ProfileListViewAdapter.ItemClickListener
{
    static private final int DEFAULT_ICON_NUM = 4;
    String selected_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ListView profileList = findViewById(R.id.profile_list);

        String [] names = new String[DEFAULT_ICON_NUM];
        Drawable [] icons = new Drawable[DEFAULT_ICON_NUM];
        for(int i=0; i<DEFAULT_ICON_NUM; i++)
        {
            names[i] = getString(R.string.default_promote)+"_"+i;
            icons[i] = getDrawable(getResources().getIdentifier("profile_"+i,"drawable",getPackageName()));
        }

        ProfileListViewAdapter viewAdapter = new ProfileListViewAdapter(this, names, icons);
        viewAdapter.setClickListener(this);
        profileList.setAdapter(viewAdapter);
    }

    @Override
    protected void onStop()
    {
        SharedPreferences sp = getSharedPreferences("config", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("profile_idx",selected_profile);
        //using the editor.apply to commit the change in background
        editor.apply();
        super.onStop();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        selected_profile = Integer.toString(position);
        Intent resetIcon = new Intent();
        resetIcon.setData(Uri.parse(selected_profile));
        setResult(RESULT_OK, resetIcon);

        finish();
    }
}
