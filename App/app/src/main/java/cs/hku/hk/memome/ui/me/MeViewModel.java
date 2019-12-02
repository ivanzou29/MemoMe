package cs.hku.hk.memome.ui.me;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cs.hku.hk.memome.jdbc.OwnJdbcDao;
import cs.hku.hk.memome.model.Own;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the ME fragment. Especially for the gift. Loading & expose the types / amount
 * of the gifts and coins for the ME fragment.
 */
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

    /**
     * Expose the fragment title
     * @return The fragment title
     */
    public LiveData<String> getText() {
        return mText;
    }

    /**
     * Load details from servers and update the stored gift info
     */
    void upDateGiftsInfo(String email)
    {
        giftTypes.clear();
        giftsNumber.clear();

        OwnJdbcDao ownJdbcDao = new OwnJdbcDao();
        ArrayList<Own> owns = new ArrayList<Own>(ownJdbcDao.getOwnsByEmail(email));

        int i;
        for (i = 0; i < owns.size(); i++) {
            Own own = owns.get(i);
            giftTypes.add(own.getGiftName());
            giftsNumber.add(own.getQuantity());
        }
    }

    /**
     * Retrieve all the types of gifts owned by the user.
     * @return An arrayList of all the types.
     */
    ArrayList<String> getGiftTypes()
    {
        return giftTypes;
    }


    /**
     * Retrieve the numbers of the gifts of every type owned by the user
     * @return An arrayList containing number of gifts, in the same sequence as the returned value
     *         of .getGiftTypes()
     */
    ArrayList<Integer> getGiftsNumber()
    {
        return giftsNumber;
    }
}