package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the To Do fragment. Loading & expose item data for the fragment.
 */
public class ToDoViewModel extends ViewModel
{
    final public static int MAX_TODO_ITEM_PER_LOAD = 9;
    private List<String> myDataList; //the list of titles
    private List<TodoDetail> myDataBaseList; //the list of contents

    public ToDoViewModel()
    {

        myDataBaseList = new ArrayList<>();
        myDataList = new ArrayList<>();

    }

    /**
     * Load both a list of titles and a list of content from the server
     * @return The list of title
     */
    public List<String> getMyData()
    {
        myDataList.clear();
        myDataBaseList.clear();
        //TODO: Change the below fake data into query results
        String[] data0 = {"COMP3278", "MATH2241"};
        String[] data1 = {"COMP3230", "COMP3258", "CUND9003"};
        String[] data2 = {"COMP3330"};

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
        return myDataList;
    }

    /**
     * Searching for the content with a given title
     * @param title The specified title
     * @return All the entries within its content.
     */
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

    /**
     * Load more titles and content from the server
     * @return The list of all title
     */
    public List<String> getNewData()
    {
        //TODO: Change the below fake data into query results
        String[] data0 = {"COMP3278", "MATH2241"};
        String[] data1 = {"COMP3230", "COMP3258", "CUND9003"};
        String[] data2 = {"COMP3330"};

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

        return myDataList;
    }

    /**
     * Abstraction for each to do item
     */
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

    /**
     * Load the status of each entry in the given to do item
     * @param title The item title
     * @return An array containing all the statuses, in the same sequence as .getListDetails(title)
     */
    public boolean [] loadTaskResuls(String title)
    {
        //TODO: Load from the database
        boolean [] ret = new boolean[getListDetails(title).length];
        for (int i=0; i<ret.length; i++)
        {
            ret[i]=0==i%2;
        }
        return ret;
    }

}

