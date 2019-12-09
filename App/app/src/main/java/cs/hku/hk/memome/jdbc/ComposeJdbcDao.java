package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.ComposeDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Compose;
import cs.hku.hk.memome.model.Own;

public class ComposeJdbcDao implements ComposeDao {
    private static DatabaseUtilities databaseUtilities = new DatabaseUtilities();
    @Override
    public Collection<String> getPostIdsByEmail(String email) {
        String sql = "SELECT * FROM Compose WHERE email = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> postIds = new ArrayList<String>();
            while (rs.next()) {
                postIds.add(rs.getString("post_id"));
            }
            ptmt.close();
            conn.close();
            return postIds;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }
    }

    @Override
    public void insertCompose(Compose compose) {
        String sql = "INSERT INTO Compose (email, post_id) " +
                "VALUES (?,?)";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, compose.getEmail());
            ptmt.setString(2, compose.getPostId());
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCompose(String postId) {
        String sql = "DELETE FROM Compose WHERE post_id = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public String getUserEmailByPostId(String postId) {
        String sql = "SELECT email FROM Compose WHERE post_id = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> postIds = new ArrayList<String>();
            if (rs.next()) {
                String email = rs.getString("email");
                ptmt.close();
                conn.close();
                return email;
            } else {
                ptmt.close();
                conn.close();
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

}
