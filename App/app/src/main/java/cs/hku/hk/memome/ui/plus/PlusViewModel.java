package cs.hku.hk.memome.ui.plus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is New fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}