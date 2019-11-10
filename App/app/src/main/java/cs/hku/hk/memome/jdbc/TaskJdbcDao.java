package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.TaskDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Task;

public class TaskJdbcDao implements TaskDao {

    @Override
    public Collection<String> getListNamesByEmail(String email) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT DISTINCT list_name FROM Tasks WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> listNames = new ArrayList<String>();
            while (rs.next()) {
                String listName = rs.getString("list_name");
                listNames.add(listName);
            }
            conn.close();
            ptmt.close();
            return listNames;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }
    }

    @Override
    public Collection<Task> getTasksByEmailAndListName(String email, String listName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT * FROM Tasks WHERE email = ? AND list_name = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.setString(2, listName);
            ResultSet rs = ptmt.executeQuery();
            Collection<Task> tasks = new ArrayList<Task>();
            while (rs.next()) {
                String taskName = rs.getString("task_name");
                Date deadline = rs.getDate("deadline");
                Boolean isFinished = rs.getBoolean("is_finished");
                Task task = new Task(email, listName, taskName, deadline, isFinished);
                tasks.add(task);
            }
            conn.close();
            ptmt.close();
            return tasks;
        } catch (SQLException e) {
            return new ArrayList<Task>();
        }
    }

    @Override
    public void insertTask(Task task) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO Tasks (email, list_name, task_name, deadline, is_finished) " +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, task.getEmail());
            ptmt.setString(2, task.getListName());
            ptmt.setString(3, task.getTaskName());
            ptmt.setDate(4, task.getDeadline());
            ptmt.setBoolean(5, task.getFinished());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteTask(String email, String listName, String taskName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Tasks WHERE email = ? AND list_name = ? AND task_name = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.setString(2, listName);
            ptmt.setString(3, taskName);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteList(String email, String listName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Tasks WHERE email = ? AND list_name = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.setString(2, listName);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
}
