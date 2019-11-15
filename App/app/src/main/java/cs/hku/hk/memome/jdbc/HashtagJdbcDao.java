package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.HashtagDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Hashtag;

public class HashtagJdbcDao implements HashtagDao {
    private static Connection conn = DatabaseUtilities.openConnection();

    @Override
    public void insertHashtag(Hashtag hashtag) {
        String sql = "INSERT INTO Hashtags (tag_name) " +
                "VALUES (?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, hashtag.getTagName());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteHashtag(String tagName) {
        String sql = "DELETE FROM Hashtags WHERE tag_name = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, tagName);
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
}
