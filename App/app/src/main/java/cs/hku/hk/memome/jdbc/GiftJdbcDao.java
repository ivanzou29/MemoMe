package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.GiftDao;
import cs.hku.hk.memome.database.DatabaseUtilities;

public class GiftJdbcDao implements GiftDao {
    @Override
    public int getValueByGiftName(String giftName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT value FROM Gifts WHERE gift_name = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, giftName);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                int value = rs.getInt("value");
                conn.close();
                ptmt.close();
                return value;
            }
            conn.close();
            ptmt.close();
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }
}
