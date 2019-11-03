package cs.hku.hk.memome.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    //TODO don't know what data type we will get from server yet
    private String[] myData;//the history diaries

    public HistoryViewModel() {
        mText = new MutableLiveData<>();
        myData = new String[0];
        mText.setValue("This is history fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String[] getMyData() {
        //TODO get data from server
        return myData;
    }
}