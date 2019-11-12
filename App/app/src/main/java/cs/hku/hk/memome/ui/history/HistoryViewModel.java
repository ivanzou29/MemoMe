package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    //TODO don't know what data type we will get from server yet
    final public int MAX_DIARIES_PER_QUERY = 9;
    private List<Diary> diaryList;

    public HistoryViewModel()
    {

        diaryList = new ArrayList<>();


        for(int i = 0; i<MAX_DIARIES_PER_QUERY; i++)
        {
            diaryList.add(new Diary("This is diary "+i, "This is the content of diary "+i));
        }

    }

    public String[] getTitles() //get all the titles
    {
        String[] titles;
        List<String> titleList = new ArrayList<>();
        for (Diary each : diaryList)
        {
            titleList.add(each.title);
        }
        titles = titleList.toArray(new String[0]);
        return  titles;
    }

    public String getContents(String title)
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