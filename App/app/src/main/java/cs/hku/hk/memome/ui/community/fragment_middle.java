package cs.hku.hk.memome.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cs.hku.hk.memome.R;

public class fragment_middle extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fagment_community_middle, container, false);
        return root;
    }
}
