package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ToDoViewModel extends ViewModel
{
    final public int MAX_TODO_ITEM_PER_LOAD = 9;
    private List<String> myDataList; //the list names
    private List<TodoDetail> myDataBaseList;

    public ToDoViewModel()
    {
        String[] data0 = {"COMP3278", "MATH2241"};
        String[] data1 = {"COMP3230", "COMP3258", "CUND9003"};
        String[] data2 = {"COMP3330"};

        myDataBaseList = new ArrayList<>();
        myDataList = new ArrayList<>();

        for(int i=0; i<MAX_TODO_ITEM_PER_LOAD; i++)
        {
            myDataList.add("Nov. "+i);
            if(i%3==0)
                myDataBaseList.add(new TodoDetail("Nov. "+i, data0));
            else if(i%3==1)
                myDataBaseList.add(new TodoDetail("Nov. "+i, data1));
            else
                myDataBaseList.add(new TodoDetail("Nov. "+i, data2));

        }

    }

    public String[] getMyData()
    {
        return myDataList.toArray(new String[0]);
    }

    public String[] getListDetails(String title)
    {
        String[] details = {"nothing to do yet"};
        for(TodoDetail each:myDataBaseList)
        {
            if(each.title.equals(title) && each.lists!=null)
                details = each.lists;
        }
        return details;
    }

    public class TodoDetail {
        String title;
        String[] lists;

        TodoDetail(String title, String [] lists)
        {
            this.title = title;
            this.lists = lists;
        }
        TodoDetail()
        {
            this("",null);
        }
    }

}

