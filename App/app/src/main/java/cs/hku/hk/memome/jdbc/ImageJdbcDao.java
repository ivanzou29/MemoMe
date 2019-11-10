package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.ImageDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Image;
import cs.hku.hk.memome.model.Own;

public class ImageJdbcDao implements ImageDao {

    @Override
    public Collection<byte[]> getImagesByPostId(String postId) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT * FROM Images WHERE postId = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();
            Collection<byte[]> images = new ArrayList<byte[]>();
            while (rs.next()) {
                byte[] image = rs.getBytes("image_bytearray");
                images.add(image);
            }
            conn.close();
            ptmt.close();
            return images;
        } catch (SQLException e) {
            return new ArrayList<byte[]>();
        }
    }

    @Override
    public void insertImage(Image image) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO Images (image_bytearray, image_id, post_id) " +
                "VALUES (?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setBytes(1, image.getImageBytearray());
            ptmt.setInt(2, image.getImageId());
            ptmt.setString(3, image.getPostId());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteImagesByPostId(String postId) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Images WHERE post_id = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

}
