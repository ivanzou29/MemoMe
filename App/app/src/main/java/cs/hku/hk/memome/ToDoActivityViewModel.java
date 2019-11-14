package cs.hku.hk.memome;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

class ToDoActivityViewModel extends ViewModel
{
    //interact with the database
    //collect the user input from ToDoActivity
    //TODO: implement the logic
    private String title;
    private List<SubTasks> allSubTasks;
    private int remained;

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

    String getTitle(){return title;}
    int getRemained(){return remained;}

    void updateCompletion(int position, boolean mStatus)
    {
        if(position >= allSubTasks.size() || remained > allSubTasks.size())
            return;
        if(allSubTasks.get(position).complete != mStatus)
            remained += mStatus?-1:1; //if true <=> uncompleted -> completed, -1
        allSubTasks.get(position).complete = mStatus;
    }

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
