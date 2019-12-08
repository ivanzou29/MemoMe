package cs.hku.hk.memome.ui.todo;

import androidx.lifecycle.ViewModel;
import cs.hku.hk.memome.jdbc.TaskJdbcDao;
import cs.hku.hk.memome.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the To Do fragment. Loading & expose item data for the fragment.
 */
public class ToDoViewModel extends ViewModel
{
    final public static int MAX_TODO_ITEM_PER_LOAD = 9;
    private List<String> myLists; //the list of titles
    private List<String> myTaskNames; //the list of contents
    private List<Task> myTasks;
    private String email;

    public ToDoViewModel()
    {

        myLists = new ArrayList<>();
        myTaskNames = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Load both a list of titles and a list of content from the server
     * @return The list of title
     */
    public List<String> getMyData()
    {
        myLists.clear();

        TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
        ArrayList<String> allLists = new ArrayList<String>(taskJdbcDao.getListNamesByEmail(email));

        for (int i = 0; i < MAX_TODO_ITEM_PER_LOAD && i < allLists.size(); i++) {
            myLists.add(allLists.get(i));
        }
        return myLists;
    }

    /**
     * Searching for the content with a given title
     * @param title The specified title
     * @return All the entries within its content.
     */
    public String[] getListDetails(String title)
    {
        myTaskNames.clear();

        TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
        myTasks = new ArrayList<Task>(taskJdbcDao.getTasksByEmailAndListName(email, title));

        for (int i = 0; i < myTasks.size(); i++) {
            myTaskNames.add(myTasks.get(i).getTaskName());
        }

        return myTaskNames.toArray(new String[0]);
    }

    /**
     * Load more titles and content from the server
     * @return The list of all title
     */
    public List<String> getNewData()
    {
        TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
        ArrayList<String> allLists = new ArrayList<String>(taskJdbcDao.getListNamesByEmail(email));
        ArrayList<String> newLists = new ArrayList<String>();
        for (int i = 0;  i < allLists.size(); i++) {
            if (myLists.indexOf(allLists.get(i)) == -1 ) {
                newLists.add(allLists.get(i));
            }
        }

        return newLists;
    }


    /**
     * Load the status of each entry in the given to do item
     * @param title The item title
     * @return An array containing all the statuses, in the same sequence as .getListDetails(title)
     */
    public boolean [] loadTaskResults(String title)
    {
        boolean [] ret = new boolean[myTaskNames.size()];

        for (int i = 0; i < myTasks.size(); i++) {
            ret[i] = myTasks.get(i).getFinished();
        }

        return ret;
    }

}

