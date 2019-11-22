package cs.hku.hk.memome.ui.community;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Adapter solely for the sub pages of the CommunityFragment
 */
public class FragAdapter extends FragmentPagerAdapter
{

    private List<Fragment> mFragments;
    private String[] tabTitle = new String[3];

    public FragAdapter(FragmentManager fm, List<Fragment> fragments, Context context)
    {
        super(fm);

        mFragments = fragments;
        Resources res = context.getResources();
        tabTitle[0] = res.getString(res.getIdentifier("tab_left", "string",context.getPackageName()));
        tabTitle[1] = res.getString(res.getIdentifier("tab_middle", "string",context.getPackageName()));
        tabTitle[2] = res.getString(res.getIdentifier("tab_right", "string",context.getPackageName()));
        Log.d("Tab_Debug", "Left: "+tabTitle[0]);
        Log.d("Tab_Debug", "Middle: "+tabTitle[1]);
        Log.d("Tab_Debug", "Right: "+tabTitle[2]);
    }

    @Override
    public Fragment getItem(int arg0)
    {
        Log.d("Tab_Debug","Selection: "+arg0+": "+tabTitle[arg0]);
        return mFragments.get(arg0);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}