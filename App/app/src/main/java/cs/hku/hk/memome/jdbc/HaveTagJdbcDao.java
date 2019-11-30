package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.HaveTagDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.HaveTag;

public class HaveTagJdbcDao implements HaveTagDao {
    private static DatabaseUtilities databaseUtilities = new DatabaseUtilities();
    @Override
    public Collection<String> getTagNamesByPostId(String postId) {
        String sql = "SELECT tag_name FROM HaveTag WHERE post_id = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();
            Collection<String> tagNames = new ArrayList<String>();
            while (rs.next()) {
                String tagName = rs.getString("tag_name");
                tagNames.add(tagName);
            }
            ptmt.close();
            conn.close();
            return tagNames;
        } catch (SQLException e) {
            return new ArrayList<String>();
        }
    }

    @Override
    public void insertHaveTag(HaveTag haveTag) {
        String sql = "INSERT INTO HaveTag (post_id, tag_name) " +
                "VALUES (?,?)";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, haveTag.getPostId());
            ptmt.setString(2, haveTag.getTagName());
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteHaveTag(String postId) {
        String sql = "DELETE FROM HaveTag WHERE post_id = ? ";
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
