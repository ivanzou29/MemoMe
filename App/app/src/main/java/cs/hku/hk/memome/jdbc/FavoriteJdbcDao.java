package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.FavoriteDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Favorite;

public class FavoriteJdbcDao implements FavoriteDao {
    
    @Override
    public Collection<String> getPostIdsByEmail(String email) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT * FROM Favorite WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> postIds = new ArrayList<String>();
            while (rs.next()) {
                postIds.add(rs.getString("post_id"));
            }
            conn.close();
            ptmt.close();
            return postIds;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }
    }

    @Override
    public void insertFavorite(Favorite favorite) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO Favorite (email, post_id) " +
                "VALUES (?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, favorite.getEmail());
            ptmt.setString(2, favorite.getPostId());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Favorite WHERE email = ? AND postId = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, favorite.getEmail());
            ptmt.setString(2, favorite.getPostId());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
    
}
