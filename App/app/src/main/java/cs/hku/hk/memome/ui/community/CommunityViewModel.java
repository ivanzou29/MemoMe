package cs.hku.hk.memome.ui.community;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cs.hku.hk.memome.jdbc.PostJdbcDao;
import cs.hku.hk.memome.model.Post;

/**
 * The view model for the community fragment and its sub fragments (pages). It holds the internal states
 * and communicate with the backend.
 */
public class CommunityViewModel extends ViewModel {

    final private static int MAX_DIARIES_PER_QUERY = 9;
    final static int LEFT_TAB = 0;
    final static int MIDDLE_TAB = 1;
    final static int RIGHT_TAB = 2;
    private List<Post> [] diaryList;

    private List<String> [] titleList;

    @SuppressWarnings("unchecked")
    public CommunityViewModel()
    {
        diaryList = new ArrayList [3];
        titleList = new ArrayList [3];
        for(int i=0; i<3; i++)
        {
            diaryList[i] = new ArrayList<>();
            titleList[i] = new ArrayList<>();
        }
    }

    /**
     * Retrieve all the title of diary in the given page
     * @param i The index of the page, one of LEFT_TAB, MIDDLE_TAB and RIGHT_TAB
     * @return The list of all the titles in that page
     */
    public List<String> getTitles(int i) //get all the titles
    {
        titleList[i].clear();
        diaryList[i].clear();
        PostJdbcDao postJdbcDao = new PostJdbcDao();
        diaryList[0].addAll(postJdbcDao.getAllNewPost());
        diaryList[1].addAll(postJdbcDao.getAllHotPost());
        diaryList[2].addAll(postJdbcDao.getAllNewPost());
        for(int k = 0; k < 3; k++){
            if(diaryList[k].size() > MAX_DIARIES_PER_QUERY){
                diaryList[k] = diaryList[k].subList(0, MAX_DIARIES_PER_QUERY);
            }
        }

        for (Post each : diaryList[i])
        {
            titleList[i].add(each.getTitle());
        }
        return titleList[i];
    }

    /**
     * Retrieve the content of a given diary
     * @param i The list of all the titles in that page
     * @param title The title of the diary
     * @return A string for the content.
     */
    public String getContents(int i, String title)
    {
        for (Post each: diaryList[i])
        {
            if(title.equals(each.getTitle()))
                return each.getText();
        }
        return "no diary is found";
    }


}