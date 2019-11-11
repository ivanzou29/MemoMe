package cs.hku.hk.memome.ui.community;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommunityViewModel extends ViewModel {

    //TODO don't know what data type we will get from server yet
    final public static int MAX_DIARIES_PER_QUERY = 9;
    final public static int LEFT_TAB = 0;
    final public static int MIDDEL_TAB = 1;
    final public static int RIGHT_TAB = 2;
    private List<Diary> [] diaryList;

    @SuppressWarnings("unchecked")
    public CommunityViewModel()
    {
        diaryList = new ArrayList [3];
        for(int i=0; i<3; i++)
        {
            diaryList[i] = new ArrayList<>();
            for(int j = 0; j<MAX_DIARIES_PER_QUERY; j++)
            {
                diaryList[i].add(new Diary("This is diary "+j+"in tab"+i, "This is the content of diary "+(i*3+j)));
            }
        }

    }

    public String[] getTitles(int i) //get all the titles
    {
        String[] titles;
        List<String> titleList = new ArrayList<>();
        for (Diary each : diaryList[i])
        {
            titleList.add(each.title);
        }
        titles = titleList.toArray(new String[0]);
        return  titles;
    }

    public String getContents(int i, String title)
    {
        for (Diary each: diaryList[i])
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