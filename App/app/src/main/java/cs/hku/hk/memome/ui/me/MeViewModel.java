package cs.hku.hk.memome.ui.me;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MeViewModel extends ViewModel
{

    private MutableLiveData<String> mText;
    private ArrayList<String> giftTypes;
    private ArrayList<Integer> giftsNumber;

    public MeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Me fragment");
        giftTypes = new ArrayList<>();
        giftsNumber = new ArrayList<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    void upDateGiftsInfo()
    {
        giftTypes.clear();
        //TODO use query to update the values in the two arraylists
        String [] sampleTypes = new String[] { "Flower", "Cake", "Teddy Bear" };
        for (String eachType : sampleTypes)
        {
            giftsNumber.add(giftTypes.size()+1);
            giftTypes.add(eachType);

        }
    }

    ArrayList<String> getGiftTypes()
    {
        return giftTypes;
    }

    ArrayList<Integer> getGiftsNumber()
    {
        return giftsNumber;
    }
}