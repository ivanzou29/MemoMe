package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String[] myData;//the list names

    public ToDoViewModel() {
        mText = new MutableLiveData<>();
        myData = new String[0];
        mText.setValue("This is ToDo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    String[] getMyData() {

        //TODO get myData from database
        return myData;
    }

}