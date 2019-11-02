package cs.hku.hk.memome.ui.plus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cs.hku.hk.memome.R;

public class PlusFragment extends Fragment {

    private PlusViewModel plusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plusViewModel =
                ViewModelProviders.of(this).get(PlusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plus, container, false);
        final TextView textView = root.findViewById(R.id.text_plus);
        plusViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}