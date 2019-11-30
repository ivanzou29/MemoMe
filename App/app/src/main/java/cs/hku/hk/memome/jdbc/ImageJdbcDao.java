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
    private static DatabaseUtilities databaseUtilities = new DatabaseUtilities();
    @Override
    public Collection<byte[]> getImagesByPostId(String postId) {
        String sql = "SELECT * FROM Images WHERE postId = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();
            Collection<byte[]> images = new ArrayList<byte[]>();
            while (rs.next()) {
                byte[] image = rs.getBytes("image_bytearray");
                images.add(image);
            }
            ptmt.close();
            conn.close();
            return images;
        } catch (SQLException e) {
            return new ArrayList<byte[]>();
        }
    }

    @Override
    public void insertImage(Image image) {
        String sql = "INSERT INTO Images (image_bytearray, image_id, post_id) " +
                "VALUES (?,?,?)";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setBytes(1, image.getImageBytearray());
            ptmt.setInt(2, image.getImageId());
            ptmt.setString(3, image.getPostId());
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteImagesByPostId(String postId) {
        String sql = "DELETE FROM Images WHERE post_id = ? ";
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

}
