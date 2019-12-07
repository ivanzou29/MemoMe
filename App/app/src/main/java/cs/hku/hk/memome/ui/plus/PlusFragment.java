package cs.hku.hk.memome.ui.plus;

import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AppCompatActivity;
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
import cs.hku.hk.memome.ui.ProcessingDialog;

/**
 * Fragment for the plus. Used to create new diaries (posts)
 */
public class PlusFragment extends Fragment {

    private TextView title;
    private TextView content;
    private CheckBox isPublic;
    private TextView hashTag;
    private String email;

    private ProcessingDialog processing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences sp = this.getActivity().getSharedPreferences("config", 0);
        email = sp.getString("email", "");
        View root = inflater.inflate(R.layout.fragment_plus, container, false);
        title = root.findViewById(R.id.plus_title);
        content = root.findViewById(R.id.plus_passage);
        hashTag = root.findViewById(R.id.plus_hashtag);
        isPublic = root.findViewById(R.id.plus_checkBox);

        final Button confirm = root.findViewById(R.id.confirm_new_post_button);

        processing = new ProcessingDialog(root);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processing.show();

                getView().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String t = title.getText().toString();
                        String c = content.getText().toString();
                        boolean p = isPublic.isChecked();
                        String id = t + " " + LocalDateTime.now();
                        String h = hashTag.getText().toString();
                        t = id;

                        Post post = new Post(id, p, c, t, 0);//set title to be the id

                        PostJdbcDao postJdbcDao = new PostJdbcDao();
                        postJdbcDao.insertPost(post);

                        HaveTag haveTag = new HaveTag(id, h);
                        HaveTagJdbcDao haveTagJdbcDao = new HaveTagJdbcDao();
                        haveTagJdbcDao.insertHaveTag(haveTag);

                        Compose compose = new Compose(email, id);
                        ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
                        composeJdbcDao.insertCompose(compose);

                        processing.dismiss();
                        title.setText(null);
                        content.setText(null);
                        isPublic.setChecked(false);
                        hashTag.setText(null);
                    }
                });


            }
        });
        return root;
    }
}