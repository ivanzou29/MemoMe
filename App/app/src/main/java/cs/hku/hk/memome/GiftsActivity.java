package cs.hku.hk.memome;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The activity for the Gift details, illustrating how many types and how many items does the user
 * own. The details to be shown are retrieved from intent.
 */
public class GiftsActivity extends AppCompatActivity {

    ArrayList<Map<String, Object>> list = new ArrayList<>();

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        SharedPreferences sp = getSharedPreferences("config", 0);
        email = sp.getString("email", "");

        Toolbar upperToolBar;

        upperToolBar = findViewById(R.id.toolbar_gifts);
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


        Bundle extras = getIntent().getExtras();
        ArrayList<String> gName = extras != null ? extras.getStringArrayList("Name") : null;
        ArrayList<Integer> gNum = extras != null ? extras.getIntegerArrayList("Number") : null;

        if(gName!=null && gNum!=null)
        {
            for (int i = 0; i < gName.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("Name", gName.get(i));
                map.put("Number", gNum.get(i));
                list.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(	this, list, R.layout.gift_item,
                new String[]{"Name","Number"},
                new int[]{R.id.gift_name, R.id.gift_num});

        ListView giftListView = findViewById(R.id.gift_list);
        giftListView.setAdapter(adapter);
    }
}
