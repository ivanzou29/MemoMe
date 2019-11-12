package cs.hku.hk.memome.ui.history;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cs.hku.hk.memome.DiaryActivity;
import cs.hku.hk.memome.MyRecyclerViewAdapter;
import cs.hku.hk.memome.R;
import cs.hku.hk.memome.ToDoActivity;

public class HistoryFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    private HistoryViewModel historyViewModel;
    MyRecyclerViewAdapter historyAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        String[] titles = historyViewModel.getTitles();
        RecyclerView recyclerView = root.findViewById(R.id.rvDiaries);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        historyAdapter = new MyRecyclerViewAdapter(this.getContext(), titles);
        historyAdapter.setClickListener(this);
        recyclerView.setAdapter(historyAdapter);
        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this.getContext(), "You clicked data " + historyAdapter.getItem(position) + ", which is at cell position " + position, Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(view.getContext(), DiaryActivity.class);
        intent.putExtra("title", historyAdapter.getItem(position));
        intent.putExtra("content", historyViewModel.getContents(historyAdapter.getItem(position)));
        startActivity(intent);
    }
}