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

import cs.hku.hk.memome.GiftsActivity;
import cs.hku.hk.memome.ProfileActivity;
import cs.hku.hk.memome.R;

public class MeFragment extends Fragment {

    private MeViewModel meViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        //final TextView textView = root.findViewById(R.id.text_me);
        meViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        Button gift = root.findViewById(R.id.gifts_button_in_me);
        ImageButton me = root.findViewById(R.id.profile_icon_button);
        gift.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(view.getContext(), GiftsActivity.class);
                startActivity(myIntent);
            }
        });
        me.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(myIntent);
            }
        });
        return root;
    }
}