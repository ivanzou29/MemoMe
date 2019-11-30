package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.PostDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Post;

public class PostJdbcDao implements PostDao {
    private static Connection conn = DatabaseUtilities.openConnection();
    @Override
    public Post getPostByPostId(String postId) {
        String sql = "SELECT * FROM Posts WHERE post_id = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                Boolean isPublic = rs.getBoolean("is_public");
                String text = rs.getString("text");
                String title = rs.getString("title");
                Post post = new Post(postId, isPublic, text, title);
                ptmt.close();
                return post;
            } else {
                conn.close();
                ptmt.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertPost(Post post) {
        String sql = "INSERT INTO Posts (post_id, is_pblic, text, title) " +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, post.getPostId());
            ptmt.setBoolean(2, post.getPublic());
            ptmt.setString(3, post.getText());
            ptmt.setString(4, post.getTitle());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deletePost(String postId) {
        String sql = "DELETE FROM Posts WHERE postId = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public Post getPostByPostTitle(String title){
        String sql = "SELECT * FROM Posts WHERE title = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, title);
            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                Boolean isPublic = rs.getBoolean("is_public");
                String text = rs.getString("text");
                String postId = rs.getString("post_id");
                Post post = new Post(postId, isPublic, text, title);
                ptmt.close();
                return post;
            } else {
                conn.close();
                ptmt.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
