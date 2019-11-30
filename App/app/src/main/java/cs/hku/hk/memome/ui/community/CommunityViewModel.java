package cs.hku.hk.memome.ui.community;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the community fragment and its sub fragments (pages). It holds the internal states
 * and communicate with the backend.
 */
public class CommunityViewModel extends ViewModel {

    //TODO don't know what data type we will get from server yet
    final private static int MAX_DIARIES_PER_QUERY = 9;
    final static int LEFT_TAB = 0;
    final static int MIDDLE_TAB = 1;
    final static int RIGHT_TAB = 2;
    private List<Diary> [] diaryList;

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
        //TODO: change below into queries
        for(int j = 0; j<MAX_DIARIES_PER_QUERY; j++)
        {
            diaryList[i].add(new Diary("This is diary "+j+"in tab"+i, "This is the content of diary "+(i*3+j)));
        }
        for (Diary each : diaryList[i])
        {
            titleList[i].add(each.title);
        }
        return titleList[i];
    }

    /**
     * Load more diaries from the server.
     * @param i The index of the page, one of LEFT_TAB, MIDDLE_TAB and RIGHT_TAB
     * @return The list of all the titles in that page
     */
    public List<String> getNewData(int i)
    {
        //TODO: change below into queries
        for(int j = 0; j<MAX_DIARIES_PER_QUERY; j++)
        {
            diaryList[i].add(new Diary("This is diary "+j+"in tab"+i, "This is the content of diary "+(i*3+j)));
        }
        for (Diary each : diaryList[i])
        {
            titleList[i].add(each.title);
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
        for (Diary each: diaryList[i])
        {
            if(title.equals(each.title))
                return each.content;
        }
        return "no diary is found";
    }

    /**
     * Abstraction for the diary (each post)
     */
    public class Diary {
        String title;
        String content;

        Diary(String  _title, String _content)
        {
            this.title = _title;
            this.content = _content;
        }
        Diary()
        {
            this("", "");
        }
    }
}