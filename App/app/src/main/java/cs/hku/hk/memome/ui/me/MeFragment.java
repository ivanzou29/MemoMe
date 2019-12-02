package cs.hku.hk.memome.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private String email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sp = this.getActivity().getSharedPreferences("config", 0);
        email = sp.getString("email", "");
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        Button gift = root.findViewById(R.id.gifts_button_in_me);
        Button logOut = root.findViewById(R.id.log_out_button_in_me);

        gift.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                meViewModel.upDateGiftsInfo(email);

                Intent myIntent = new Intent(view.getContext(), GiftsActivity.class);
                myIntent.putStringArrayListExtra("Name", meViewModel.getGiftTypes());
                myIntent.putIntegerArrayListExtra("Number", meViewModel.getGiftsNumber());
                startActivity(myIntent);
            }
        });
        logOut.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                SharedPreferences sp = MeFragment.this.getActivity().getSharedPreferences("config", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                myIntent.putExtra("loggingOut", true);
                startActivity(myIntent);
            }
        });
        return root;
    }
}