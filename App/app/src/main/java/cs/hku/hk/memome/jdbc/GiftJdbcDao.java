package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.GiftDao;
import cs.hku.hk.memome.database.DatabaseUtilities;

public class GiftJdbcDao implements GiftDao {
    private static Connection conn = DatabaseUtilities.openConnection();
    @Override
    public int getValueByGiftName(String giftName) {
        String sql = "SELECT value FROM Gifts WHERE gift_name = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, giftName);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                int value = rs.getInt("value");
                ptmt.close();
                return value;
            }
            ptmt.close();
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }
}
