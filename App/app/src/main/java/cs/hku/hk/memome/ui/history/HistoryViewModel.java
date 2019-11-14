package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

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

    String getContents(String title)
    {
        for (Diary each: diaryList)
        {
            if(title.equals(each.title))
                return each.content;
        }
        return "no diary is found";
    }

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