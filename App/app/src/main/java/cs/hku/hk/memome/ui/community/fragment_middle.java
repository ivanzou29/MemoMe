package cs.hku.hk.memome.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cs.hku.hk.memome.DiaryActivity;
import cs.hku.hk.memome.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;

public class fragment_middle extends Fragment implements MyRecyclerViewAdapter.ItemClickListener
{
    private CommunityViewModel communityViewModel;
    private MyRecyclerViewAdapter communityAdapter;
    static final private int NUM_COLUMN = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        communityViewModel = ViewModelProviders.of(this).get(CommunityViewModel.class);
        String [] titles = communityViewModel.getTitles(CommunityViewModel.MIDDEL_TAB);

        View root = inflater.inflate(R.layout.fagment_community_middle, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rvMiddleDiaries);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), NUM_COLUMN));

        communityAdapter = new MyRecyclerViewAdapter(this.getContext(), titles);
        communityAdapter.setClickListener(this);
        recyclerView.setAdapter(communityAdapter);


        return root;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Toast.makeText(this.getContext(), "You clicked data " + communityAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(view.getContext(), DiaryActivity.class);
        intent.putExtra("title", communityAdapter.getItem(position));
        intent.putExtra("content",communityViewModel.getContents(CommunityViewModel.MIDDEL_TAB,communityAdapter.getItem(position)));
        startActivity(intent);
    }
}
