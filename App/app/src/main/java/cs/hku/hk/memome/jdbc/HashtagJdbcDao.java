package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.HashtagDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Hashtag;

public class HashtagJdbcDao implements HashtagDao {

    @Override
    public void insertHashtag(Hashtag hashtag) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO Hashtags (tag_name) " +
                "VALUES (?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, hashtag.getTagName());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteHashtag(String tagName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Hashtags WHERE tag_name = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, tagName);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
}
