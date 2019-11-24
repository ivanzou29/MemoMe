package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is view model bound for HistoryFragment. Offering data support for the fragment.
 */
public class HistoryViewModel extends ViewModel {

    //TODO don't know what data type we will get from server yet
    final private int MAX_DIARIES_PER_QUERY = 11;
    private List<Diary> diaryList;
    private List<String> titleList;

    public HistoryViewModel()
    {

        diaryList = new ArrayList<>();
        titleList = new ArrayList<>();
    }

    /**
     * Load the titles of the historical post from servers
     * @return A list containing limited numbers of the post titles
     */
    List<String> getTitles() //get all the titles
    {
        diaryList.clear();
        titleList.clear();

        //TODO: Change this to be the query results
        for(int i = 0; i<MAX_DIARIES_PER_QUERY; i++)
        {
            diaryList.add(new Diary("This is diary "+i, "This is the content of diary "+i));
        }
        for (Diary each : diaryList)
        {
            titleList.add(each.title);
        }
        return titleList;
    }

    /**
     * Load more titles from servers
     * @return A list containing all the titles
     */
    List<String> getNewData()
    {
        for(int j = 0; j<MAX_DIARIES_PER_QUERY; j++)
        {
            //TODO: Change this to be the query results
            diaryList.add(new Diary("This is diary "+j, "This is the content of diary "+j));
        }
        titleList.clear();
        for (Diary each : diaryList)
        {
            titleList.add(each.title);
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
        for (Diary each: diaryList)
        {
            if(title.equals(each.title))
                return each.content;
        }
        return "no diary is found";
    }

    /**
     * Abstraction for the previous diary by the same writer.
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