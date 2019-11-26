package cs.hku.hk.memome.ui.plus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ActivityNavigator;

import cs.hku.hk.memome.R;
import cs.hku.hk.memome.dao.HaveTagDao;
import cs.hku.hk.memome.jdbc.ComposeJdbcDao;
import cs.hku.hk.memome.jdbc.HaveTagJdbcDao;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.model.Compose;
import cs.hku.hk.memome.model.HaveTag;
import cs.hku.hk.memome.model.Post;

import static android.content.Intent.getIntent;

/**
 * Fragment for the plus. Used to create new diaries (posts)
 */
public class PlusFragment extends Fragment {

    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_plus, container, false);
        title = root.findViewById(R.id.plus_title);
        content = root.findViewById(R.id.plus_passage);
        hashTag = root.findViewById(R.id.plus_hashtag);
        isPublic = root.findViewById(R.id.plus_checkBox);
        try {
            email = getArguments().getString("email");
        } catch (Exception e) {
            email = "anonymous";
            Toast.makeText(getContext(), "Login status is not successful.", Toast.LENGTH_LONG).show();
        }

        final Button confirm = root.findViewById(R.id.confirm_new_post_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send new post to the DB, assign an unique ID
                String t = title.getText().toString();
                String c = content.getText().toString();
                Boolean p = isPublic.isChecked();
                String h = hashTag.getText().toString();
                String id = t + " " + LocalDateTime.now();

                Post post = new Post(id, p, c, t, 0);//set title to be the id

                PostJdbcDao postJdbcDao = new PostJdbcDao();
                postJdbcDao.insertPost(post);

                HaveTag haveTag = new HaveTag(id, h);
                HaveTagJdbcDao haveTagJdbcDao = new HaveTagJdbcDao();
                haveTagJdbcDao.insertHaveTag(haveTag);

                Compose compose = new Compose(email, id);
                ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                composeJdbcDao.insertCompose(compose);

                Toast.makeText(v.getContext(), "you title is " + t + "\nconfirm your post", Toast.LENGTH_LONG).show();

                title.setText(null);
                content.setText(null);
                isPublic.setChecked(false);
                hashTag.setText(null);


            }
        });
        return root;
    }
}