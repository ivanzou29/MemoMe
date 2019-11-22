package cs.hku.hk.memome.ui.community;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cs.hku.hk.memome.PostActivity;
import cs.hku.hk.memome.uiAdapter.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;

/**
 * Fragment for the left tab inside the Community Fragment. Implementing infinite scrolling, shaking
 * to read more and scrolling down to refresh.
 */
public class fragment_left extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyRecyclerViewAdapter.ItemClickListener
{
    private CommunityViewModel communityViewModel;
    private MyRecyclerViewAdapter communityAdapter;
    static final private int NUM_COLUMN = 1;

    private List<String> allTitles;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SensorManager sensorManager;
    private Vibrator vibrator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);
        allTitles = communityViewModel.getTitles(CommunityViewModel.LEFT_TAB);

        View root = inflater.inflate(R.layout.fagment_community_left, container, false);

        recyclerView = root.findViewById(R.id.rvLeftDiaries);
        layoutManager = new GridLayoutManager(this.getContext(), NUM_COLUMN);
        recyclerView.setLayoutManager(layoutManager);

        communityAdapter = new MyRecyclerViewAdapter(this.getContext(), allTitles);
        communityAdapter.setClickListener(this);
        recyclerView.setAdapter(communityAdapter);

        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_left);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                reloadEntireContent();
            }
        });

        sensorManager = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);

        return root;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Toast.makeText(this.getContext(), "You clicked data " + communityAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(view.getContext(), PostActivity.class);
        intent.putExtra("title", communityAdapter.getItem(position));
        intent.putExtra("content",communityViewModel.getContents(CommunityViewModel.LEFT_TAB,communityAdapter.getItem(position)));
        startActivity(intent);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(sensorManager != null)
        {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(sensorManager != null)
        {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void onRefresh()
    {
        AlertDialog.Builder bb = new AlertDialog.Builder(getContext());
        bb.setPositiveButton(getString(R.string.bb_positive), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                reloadEntireContent();
            }
        });
        bb.setNegativeButton(getString(R.string.bb_negative), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        bb.setMessage(getString(R.string.load_community));
        bb.show();
    }


    private void reloadEntireContent()
    {
        swipeRefreshLayout.setRefreshing(true);
        allTitles = communityViewModel.getTitles(CommunityViewModel.LEFT_TAB);
        communityAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener()
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float [] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            int medumValue = 19;
            if(Math.abs(x) > medumValue || Math.abs(y)>medumValue || Math.abs(z)>medumValue)
            {
                long [] pattern = {100,100};
                vibrator.vibrate(VibrationEffect.createWaveform(pattern,-1));
                Toast.makeText(recyclerView.getContext(),R.string.loading_new_items,Toast.LENGTH_SHORT).show();

                int originalSize = allTitles.size();
                allTitles = communityViewModel.getNewData(CommunityViewModel.LEFT_TAB);
                communityAdapter.notifyItemInserted(originalSize);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };
}
