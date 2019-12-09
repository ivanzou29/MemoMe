package cs.hku.hk.memome.dao;

import java.util.Collection;

import cs.hku.hk.memome.model.Task;

public interface TaskDao {

    Collection<String> getListNamesByEmail(String email);

    Collection<Task> getTasksByEmailAndListName(String email, String listName);

    Collection<String> getTaskNamesByEmailAndListName(String email, String listName);

    Task getTaskByEmailAndListNameAndTaskName(String email, String listName, String taskName);

    void insertTask(Task task);

    void finishTask(String email, String listName, String taskName);

    void deleteTask(String email, String listName, String taskName);

    void deleteList(String email, String listName);
}
