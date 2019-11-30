package cs.hku.hk.memome.jdbc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.model.Task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TaskJdbcDaoTest {

    private TaskJdbcDao taskJdbcDao = new TaskJdbcDao();
    private String testEmail = "test@gmail.com";
    private String testListName = "study";
    private String testTaskName = "english";
    private boolean testIsFinished = false;

    private String mockEmail = "mockuser000@gmail.com";
    private String mockListName = "learn";
    private String mockTaskName = "french";
    private boolean mockIsFinished = false;

    private String mockTaskName2 = "italian";
    private boolean mockIsFinished2 = true;

    //getListNamesByEmail should return correct list names for a certain email
    @Test
    public void test1() {
        Collection<String> listNames = new ArrayList<String>();
        listNames = taskJdbcDao.getListNamesByEmail(testEmail);
        assertEquals(listNames.size(), 1);
    }

    //getListNamesByEmail should return an empty list for a non-existing email
    @Test
    public void test2() {
        Collection<String> listNames = new ArrayList<String>();
        listNames = taskJdbcDao.getListNamesByEmail("notauser@gmail.com");
        assertEquals(listNames.size(), 0);
    }

    //getTasksByEmailAndListName should return all tasks in the list for a certain email
    @Test
    public void test3() {
        Collection<Task> tasks = new ArrayList<Task>();
        tasks = taskJdbcDao.getTasksByEmailAndListName(testEmail, testListName);
        assertEquals(tasks.size(), 3);
    }

    //getTasksByEmailAndListName should return an empty list if the list name does not exist
    @Test
    public void test4() {
        Collection<Task> tasks = new ArrayList<Task>();
        tasks = taskJdbcDao.getTasksByEmailAndListName(testEmail, "not a list");
        assertEquals(tasks.size(), 0);
    }

    //getTaskByEmailAndListNameAndTaskName should return a task if inputs are valid
    public void test5() {
        Task task = taskJdbcDao.getTaskByEmailAndListNameAndTaskName(mockEmail, mockListName, mockTaskName);
        assertEquals(task.getFinished(), mockIsFinished);
    }

    //insertTask should insert a task successfully
    @Test
    public void test6() {
        Task mockTask = new Task();
        mockTask.setEmail(mockEmail);
        mockTask.setListName(mockListName);
        mockTask.setTaskName(mockTaskName);
        mockTask.setFinished(mockIsFinished);

        Task mockTask2 = new Task();
        mockTask2.setEmail(mockEmail);
        mockTask2.setListName(mockListName);
        mockTask2.setTaskName(mockTaskName2);
        mockTask2.setFinished(mockIsFinished2);

        Collection<Task> tasksBeforeInsertion = taskJdbcDao.getTasksByEmailAndListName(mockEmail, mockListName);

        taskJdbcDao.insertTask(mockTask);
        taskJdbcDao.insertTask(mockTask2);

        Collection<Task> tasksAfterInsertion = taskJdbcDao.getTasksByEmailAndListName(mockEmail, mockListName);

        assertEquals(tasksAfterInsertion.size() - tasksBeforeInsertion.size(), 2);
    }

    //finishTask should set the task to finished
    @Test
    public void test7(){
        Task beforeFinish = taskJdbcDao.getTaskByEmailAndListNameAndTaskName(mockEmail, mockListName, mockTaskName);
        assertEquals(false, beforeFinish.getFinished());

        taskJdbcDao.finishTask(mockEmail, mockListName, mockTaskName);

        Task afterFinish = taskJdbcDao.getTaskByEmailAndListNameAndTaskName(mockEmail, mockListName, mockTaskName);
        assertEquals(true, afterFinish.getFinished());
    }

    //deleteTask should delete the corresponding task successfully
    @Test
    public void test8() {
        Task beforeDelete = taskJdbcDao.getTaskByEmailAndListNameAndTaskName(mockEmail, mockListName, mockTaskName);
        assertNotNull(beforeDelete);

        taskJdbcDao.deleteTask(mockEmail, mockListName, mockTaskName);

        Task afterDelete = taskJdbcDao.getTaskByEmailAndListNameAndTaskName(mockEmail, mockListName, mockTaskName);
        assertNull(afterDelete);
    }

    //deleteList should delete the all the tasks with the list name
    @Test
    public void test9() {

        Collection<Task> beforeDelete = taskJdbcDao.getTasksByEmailAndListName(mockEmail, mockListName);
        assertNotEquals(beforeDelete.size(), 0);

        taskJdbcDao.deleteList(mockEmail, mockListName);

        Collection<Task> afterDelete = taskJdbcDao.getTasksByEmailAndListName(mockEmail, mockListName);
        assertEquals(afterDelete.size(), 0);
    }

}
