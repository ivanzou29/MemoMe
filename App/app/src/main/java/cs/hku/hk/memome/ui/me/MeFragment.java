package cs.hku.hk.memome.ui.me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import cs.hku.hk.memome.GiftsActivity;
import cs.hku.hk.memome.MainActivity;
import cs.hku.hk.memome.ProfileActivity;
import cs.hku.hk.memome.R;
import cs.hku.hk.memome.jdbc.UserJdbcDao;
import cs.hku.hk.memome.model.User;

/**
 * Fragment for the ME tab.
 */
public class MeFragment extends Fragment {

    private MeViewModel meViewModel;
    private ImageButton iconButton;
    private String iconName;
    private String email;
    static final private int profileActivity = 11;
    private static int sIndex = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        SharedPreferences sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("config", 0);
        email = sp.getString("email", "");

        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        Button gift = root.findViewById(R.id.gifts_button_in_me);
        Button help = root.findViewById(R.id.help_button_in_me);
        Button logOut = root.findViewById(R.id.log_out_button_in_me);
        TextView coin = root.findViewById(R.id.coin);
        TextView user = root.findViewById(R.id.user);

        UserJdbcDao userJdbcDao = new UserJdbcDao();
        int coinNo = userJdbcDao.getCoinsByEmail(email);
        coin.setText("You currently have " + coinNo + " coins.");

        user.setText("Hi " + email + "!");

        iconButton = root.findViewById(R.id.profile_icon_button);
        iconButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent changeIcon = new Intent(v.getContext(),ProfileActivity.class);
                startActivityForResult(changeIcon, profileActivity);
            }
        });

        gift.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view){
                meViewModel.upDateGiftsInfo(email);

                Intent myIntent = new Intent(view.getContext(), GiftsActivity.class);
                myIntent.putStringArrayListExtra("Name", meViewModel.getGiftTypes());
                myIntent.putIntegerArrayListExtra("Number", meViewModel.getGiftsNumber());
                startActivity(myIntent);
            }
        });

        help.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(v.getContext() )
//                        .setTitle( "Help" )
//                        .setMessage( "Please send your problems to memome.app@outlook.com.\nWe will solve it for you as soon as possible.\nSorry for the inconvenience!" )
//                        .setNegativeButton( "Confirm",null )
//                        .show();
                Uri uri = Uri.parse("mailto:memome.app@outlook.com");
                String[] email = {"memome.app@outlook.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_CC, email);
                intent.putExtra(Intent.EXTRA_SUBJECT, "email subject");
                intent.putExtra(Intent.EXTRA_TEXT, "email content");
                startActivity(Intent.createChooser(intent, "choose a email client from..."));
            }
        });

        logOut.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view){
                SharedPreferences sp = MeFragment.this.getActivity().getSharedPreferences("config", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                myIntent.putExtra("loggingOut", true);
                startActivity(myIntent);
            }
        });
        return root;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        SharedPreferences sp = Objects.requireNonNull(MeFragment.this.getActivity()).getSharedPreferences("config",0);
        Log.e("DEBUG", "onResume: called with icon rendered", null);
        iconName = sp.getString("profile_idx", "0");
        Log.e("DEBUG","onResume: icon id="+iconName, null);
        int drawableId = getResources().getIdentifier("profile_"+iconName, "drawable", getActivity().getPackageName());

        iconButton.setImageDrawable(getActivity().getDrawable(drawableId));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Log.e("DEBUG", "onActivityResult: come inside", null);
        // return from ProfileActivity
        if(requestCode == profileActivity)
        {
            if(resultCode == AppCompatActivity.RESULT_OK)
            {
                String new_profile_idx = data.getDataString();
                Log.e("DEBUG", "onActivityResult: got id="+new_profile_idx, null);
                Log.e("DEBUG", "onActivityResult: origin id="+iconName, null);
                if(!iconName.equals(new_profile_idx))
                {
                    //redraw
                    Toast.makeText(getContext(),R.string.updating,Toast.LENGTH_SHORT).show();

                    iconName = new_profile_idx;
                    int drawableId = getResources().getIdentifier("profile_"+iconName, "drawable", Objects.requireNonNull(getActivity()).getPackageName());
                    try
                    {
                        iconButton.setImageDrawable(getActivity().getDrawable(drawableId));
                    }catch (Exception e)
                    {
                        iconButton.setImageDrawable
                        (
                                getActivity().getDrawable(R.drawable.profile_0) //default icon
                        );
                    }
                }
            }
        }
    }
}