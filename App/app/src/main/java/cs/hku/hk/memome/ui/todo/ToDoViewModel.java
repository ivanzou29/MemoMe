package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToDoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ToDo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}