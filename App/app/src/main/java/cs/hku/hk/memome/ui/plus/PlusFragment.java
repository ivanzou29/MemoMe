package cs.hku.hk.memome.ui.plus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cs.hku.hk.memome.R;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.model.Post;

/**
 * Fragment for the plus. Used to create new diaries (posts)
 */
public class PlusFragment extends Fragment {

    private TextView title;
    private TextView content;
    private CheckBox isPublic;
    private TextView hashTag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_plus, container, false);
        title = root.findViewById(R.id.plus_title);
        content = root.findViewById(R.id.plus_passage);
        hashTag = root.findViewById(R.id.plus_hashtag);
        isPublic = root.findViewById(R.id.plus_checkBox);

        final Button confirm = root.findViewById(R.id.confirm_new_post_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send new post to the DB, assign an unique ID
                String t = title.getText().toString();
                String c = content.getText().toString();
                Boolean p = isPublic.isChecked();
                String h = hashTag.getText().toString();

                Post post =new Post(t, p, c, t, 0);//set title to be the id


                PostJdbcDao postJdbcDao= new PostJdbcDao();
                postJdbcDao.insertPost(post);


                Toast.makeText(v.getContext(),"you title is " + t + "\nconfirm your post", Toast.LENGTH_LONG).show();

                title.setText(null);
                content.setText(null);
                isPublic.setChecked(false);
                hashTag.setText(null);


            }
        });
        return root;
    }
}