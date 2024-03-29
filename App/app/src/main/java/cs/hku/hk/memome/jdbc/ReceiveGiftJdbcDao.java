package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.ReceiveGiftDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.ReceiveGift;

public class ReceiveGiftJdbcDao implements ReceiveGiftDao {
    private static DatabaseUtilities databaseUtilities = new DatabaseUtilities();
    @Override
    public Collection<ReceiveGift> getReceiveGiftsByPostId(String postId) {
        String sql = "SELECT * FROM ReceiveGifts WHERE post_id = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ResultSet rs = ptmt.executeQuery();
            Collection<ReceiveGift> receiveGifts = new ArrayList<ReceiveGift>();
            while (rs.next()) {
                String giftName = rs.getString("gift_name");
                int quantity = rs.getInt("quantity");
                ReceiveGift receiveGift = new ReceiveGift(postId, giftName, quantity);
                receiveGifts.add(receiveGift);
            }
            ptmt.close();
            conn.close();
            return receiveGifts;
        } catch (SQLException e) {
            return new ArrayList<ReceiveGift>();
        }
    }

    @Override
    public int getGiftQuantityFromPostIdAndGiftName(String postId, String giftName) {
        String sql = "SELECT quantity FROM ReceiveGifts WHERE post_id = ? AND gift_name = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ptmt.setString(2, giftName);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                ptmt.close();
                conn.close();
                return quantity;
            }
            else {
                ptmt.close();
                conn.close();
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public void insertReceiveGift(ReceiveGift receiveGift) {
        String sql = "INSERT INTO ReceiveGifts (gift_name, post_id, quantity) " +
                "VALUES (?,?,?)";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, receiveGift.getGiftName());
            ptmt.setString(2, receiveGift.getPostId());
            ptmt.setInt(3, receiveGift.getQuantity());
            System.out.println(receiveGift.getQuantity());
            ptmt.execute();
            System.out.println("inserted successfully");
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReceiveGift(int quantity, String postId, String giftName) {
        String sql = "UPDATE ReceiveGifts SET quantity = quantity + ? WHERE post_id = ? AND gift_name = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, quantity);
            ptmt.setString(2, postId);
            ptmt.setString(3, giftName);
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteReceiveGift(String postId, String giftName) {
        String sql = "DELETE FROM ReceiveGifts WHERE gift_name = ? AND post_id = ? ";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, giftName);
            ptmt.setString(2, postId);
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }
}
