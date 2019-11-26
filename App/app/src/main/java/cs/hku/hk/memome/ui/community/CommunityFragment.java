package cs.hku.hk.memome.ui.community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cs.hku.hk.memome.R;

/**
 * This is the fragment for community, which contains three individual sub fragments. Among the three,
 * sliding is enabled.
 */
public class CommunityFragment extends Fragment {

    private CommunityViewModel communityViewModel;

    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences sp = this.getActivity().getSharedPreferences("config", 0);
        email = sp.getString("email", "");
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_community, container, false);

        initViewPager(root);

        return root;
    }

    // creating tabs & link the tabs with pages (sub fragments)
    private void initViewPager(View parent)
    {
        ViewPager vp = parent.findViewById(R.id.viewpager_community);
        List<Fragment> fglist = new ArrayList<>(3);

        fglist.add(new fragment_left());
        fglist.add(new fragment_middle());
        fglist.add(new fragment_right());

        vp.setAdapter(new FragAdapter(this.getChildFragmentManager(),fglist, parent.getContext()));
        vp.setCurrentItem(0);

        TabLayout tab = parent.findViewById(R.id.tabLayout_community);
        tab.setupWithViewPager(vp, true);
        tab.getTabAt(0).setText(getResources().getText(R.string.tab_left));
        tab.getTabAt(1).setText(getResources().getText(R.string.tab_middle));
        tab.getTabAt(2).setText(getResources().getText(R.string.tab_right));
    }
}