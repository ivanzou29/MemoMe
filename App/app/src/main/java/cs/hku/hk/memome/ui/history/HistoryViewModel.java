package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    //TODO don't know what data type we will get from server yet
    private Diary[] diaries;//the history diaries

    public HistoryViewModel() {
        diaries = new Diary[3];
        for (int i = 0; i < 3; i++) {
            diaries[i] = new Diary();
        }
        diaries[0].title = "happy";
        diaries[0].content = "I am happy!";
        diaries[1].title = "sad";
        diaries[1].content = "I am sad";
        diaries[2].title = "angry";
        diaries[2].content = "I am angry";

    }

    public String[] getTitles(){//get all the titles
        String[] titles = new String[diaries.length];
        for (int i = 0; i < diaries.length; i++) {
            titles[i] = diaries[i].title;
        }
        return titles;
    }

    public String getContents(String title) {
        for (int i = 0; i < diaries.length; i++) {
            if (title.equals(diaries[i].title)) {
                return diaries[i].content;
            }
        }
        return "no diary is found";
    }

    public class Diary {
        public String title;
        public String content;
    }
}