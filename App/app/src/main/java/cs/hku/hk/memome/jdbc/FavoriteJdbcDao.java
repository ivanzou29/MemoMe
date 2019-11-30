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
    private static Connection conn = DatabaseUtilities.openConnection();

    @Override
    public Collection<String> getPostIdsByEmail(String email) {
        String sql = "SELECT * FROM Favorite WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> postIds = new ArrayList<String>();
            while (rs.next()) {
                postIds.add(rs.getString("post_id"));
            }
            ptmt.close();
            return postIds;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }
    }

    @Override
    public void insertFavorite(Favorite favorite) {
        String sql = "INSERT INTO Favorite (email, post_id) " +
                "VALUES (?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, favorite.getEmail());
            ptmt.setString(2, favorite.getPostId());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
        String sql = "DELETE FROM Favorite WHERE email = ? AND postId = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, favorite.getEmail());
            ptmt.setString(2, favorite.getPostId());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
    
}
