package cs.hku.hk.memome.ui.plus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cs.hku.hk.memome.R;

public class PlusFragment extends Fragment {

    private PlusViewModel plusViewModel;
    private TextView title;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plusViewModel =
                ViewModelProviders.of(this).get(PlusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plus, container, false);
        title = root.findViewById(R.id.plus_title);
        Button confirm = root.findViewById(R.id.confirm_new_post_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send new post to the DB, assign an unique ID
                String t = title.getText().toString();
                Toast.makeText(v.getContext(),"you title is " + t + "\nconfirm your post", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}