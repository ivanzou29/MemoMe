package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cs.hku.hk.memome.jdbc.ComposeJdbcDao;
import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.model.Compose;
import cs.hku.hk.memome.model.Post;

/**
 * This is view model bound for HistoryFragment. Offering data support for the fragment.
 */
public class HistoryViewModel extends ViewModel {

    final private int MAX_DIARIES_PER_QUERY = 11;
    private List<Post> diaryList;
    private List<String> titleList;

    private String email;


    public HistoryViewModel()
    {

        diaryList = new ArrayList<>();
        titleList = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Load the titles of the historical post from servers
     * @return A list containing limited numbers of the post titles
     */
    List<String> getTitles() //get all the titles
    {
        titleList.clear();
        ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
        titleList = new ArrayList<String>(composeJdbcDao.getPostIdsByEmail(email));


        if(titleList.size() > MAX_DIARIES_PER_QUERY){
            titleList = titleList.subList(0, MAX_DIARIES_PER_QUERY);
        }

        return titleList;

    }

    /**
     * Load more titles from servers
     * @return A list containing all the titles
     */
    List<String> getNewData()
    {

        ComposeJdbcDao composeJdbcDao = new ComposeJdbcDao();
        titleList = new ArrayList<String>(composeJdbcDao.getPostIdsByEmail(email));


        if(titleList.size() > MAX_DIARIES_PER_QUERY){
            titleList = titleList.subList(0, MAX_DIARIES_PER_QUERY);
        }

        return titleList;
    }

    /**
     * Searching the historical diaries with the given title
     * @param title The title of the diary to be shown
     * @return The content of that diary.
     */
    String getContents(String title)
    {
        PostJdbcDao postJdbcDao = new PostJdbcDao();
        Post post = postJdbcDao.getPostByPostId(title);
        if(post != null){
            return post.getText();
        }

        return "Cannot find the diary any more!";
    }


}