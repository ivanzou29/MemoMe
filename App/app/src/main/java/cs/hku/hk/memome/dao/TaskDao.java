package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Task;

public interface TaskDao {

    Collection<String> getListNamesByEmail(String email);

    Collection<Task> getTasksByEmailAndListName(String email, String listName);

    void insertTask(Task task);

    void deleteTask(String email, String listName, String taskName);

    void deleteList(String email, String listName);
}
