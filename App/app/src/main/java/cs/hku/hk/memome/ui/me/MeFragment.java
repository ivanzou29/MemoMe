package cs.hku.hk.memome.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;

import cs.hku.hk.memome.GiftsActivity;
import cs.hku.hk.memome.MainActivity;
import cs.hku.hk.memome.ProfileActivity;
import cs.hku.hk.memome.R;

/**
 * Fragment for the ME tab.
 */
public class MeFragment extends Fragment {

    private MeViewModel meViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        Button gift = root.findViewById(R.id.gifts_button_in_me);
        Button logOut = root.findViewById(R.id.log_out_button_in_me);

        gift.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                meViewModel.upDateGiftsInfo();

                Intent myIntent = new Intent(view.getContext(), GiftsActivity.class);
                myIntent.putStringArrayListExtra("Name", meViewModel.getGiftTypes());
                myIntent.putIntegerArrayListExtra("Number", meViewModel.getGiftsNumber());
                startActivity(myIntent);
            }
        });
        logOut.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                //Todo: delete stored email and password
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                myIntent.putExtra("loggingOut", true);
                startActivity(myIntent);
            }
        });
        return root;
    }
}