package cs.hku.hk.memome;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleAdapter;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GiftsActivity extends ListActivity {

    ArrayList<Map<String, Object>> list = new ArrayList< Map<String, Object> >();
    private Toolbar upperToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gifts);

//        upperToolBar = findViewById(R.id.toolbar_gifts);
//        upperToolBar.setNavigationIcon(R.drawable.ic_return_home_24dp);
//        upperToolBar.setNavigationContentDescription(R.string.return_home);
//        setSupportActionBar(upperToolBar);
//        upperToolBar.setNavigationOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Log.d("ActivityDebug","Navigation on click event");
//                Intent returnHome = new Intent();
//                setResult(RESULT_OK, returnHome);
//                finish();
//            }
//        });


        Bundle extras = getIntent().getExtras();
        ArrayList<String> gName = extras.getStringArrayList("Name");
        ArrayList<Integer> gNum = extras.getIntegerArrayList("Number");

        for( int i = 0; i < gName.size(); i++ ){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put( "Name", gName.get(i) );
            map.put( "Number", gNum.get(i) );
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(	this, list, R.layout.gift_item,
                new String[]{"Name","Number"},
                new int[]{R.id.gift_name, R.id.gift_num}	);

        setListAdapter(adapter);
    }
}
