package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoViewModel extends ViewModel {

    private String[] myData = {"Oct.18","Nov.04","Nov.06"};//the list names
    private todo[] myDataBase = new todo[3];

    public ToDoViewModel() {
        for (int i = 0; i < 3; i++) {
            myDataBase[i] = new todo();
        }
        myDataBase[0].title = "Oct.18";
        myDataBase[1].title = "Nov.04";
        myDataBase[2].title = "Nov.06";
        String[] data0 = {"COMP3278", "MATH2241"};
        String[] data1 = {"COMP3230", "COMP3258", "CUND9003"};
        String[] data2 = {"COMP3330"};
        myDataBase[0].lists = data0;
        myDataBase[1].lists = data1;
        myDataBase[2].lists = data2;
    }

    public String[] getMyData() {
        //TODO get lists titles from database
        return myData;
    }

    public String[] getListDetails(String title) {
        String[] details = {"nothing to do yet"};
        for (int i = 0; i < myDataBase.length; i++) {
            if (myDataBase[i].title.equals(title)) {
                details = myDataBase[i].lists;
            }
        }
        return details;
    }

    public class todo {
        public String title;
        public String[] lists;
    }

}

