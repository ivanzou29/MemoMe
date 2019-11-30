package cs.hku.hk.memome;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel is bound with the ToDoActivity. Offering data support to the activity.
 * Different from ToDoViewModel which manipulate To Do item while this one takes care of the entries
 * inside each item.
 */
class ToDoActivityViewModel extends ViewModel
{
    //interact with the database
    //collect the user input from ToDoActivity
    //TODO: implement the logic
    private String title;
    private List<SubTasks> allSubTasks;
    private int remained;

    /**
     * The constructor is solely called by the ToDoActivity once when it is created.
     *
     * @param title A string storing the index of the To Do item (actually, the date)
     * @param details The content of the To Do item
     * @param finishStatus Collection of the status for all the entries in its content
     */
    ToDoActivityViewModel(String title, String [] details, boolean [] finishStatus)
    {
        this.title = title;
        allSubTasks = new ArrayList<>();
        remained = 0;

        if(details!=null)
        {
            int i = 0;
            for(String each:details)
            {
                boolean status = false;
                if(finishStatus!=null && finishStatus[i]) status=true;
                allSubTasks.add(new SubTasks(each, status));
            }
        }
    }

    /**
     * Retrieve the title of the item
     * @return A string which indexing the To Do item
     */
    String getTitle(){return title;}

    /**
     * Return to number of unfinished entries within the current item.
     * @return An int representing the number of unfinished ones.
     */
    int getRemained(){return remained;}

    /**
     * Handle the status change of one entry. Toggling between uncompleted and completed.
     * @param position The integral index of the entry whose status is to be toggled.
     * @param mStatus The target status.
     */
    void updateCompletion(int position, boolean mStatus)
    {
        if(position >= allSubTasks.size() || remained > allSubTasks.size())
            return;
        if(allSubTasks.get(position).complete != mStatus)
            remained += mStatus?-1:1; //if true <=> uncompleted -> completed, -1
        allSubTasks.get(position).complete = mStatus;
    }

    /**
     * Abstraction for each entry inside the to do item.
     */
    private class SubTasks
    {
        boolean complete;
        String taskName;
        SubTasks(String taskName, boolean complete)
        {
            this.complete=complete;
            this.taskName=taskName;

            if(!complete) remained+=1;
        }
    }


}
