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

    @Override
    public Collection<ReceiveGift> getReceiveGiftsByPostId(String postId) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT * FROM ReceiveGifts WHERE post_id = ?";
        try {
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
            conn.close();
            ptmt.close();
            return receiveGifts;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int getGiftQuantityFromPostIdAndGiftName(String postId, String giftName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT quantity FROM ReceiveGifts WHERE post_id = ? AND gift_name = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, postId);
            ptmt.setString(2, giftName);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                conn.close();
                ptmt.close();
                return quantity;
            }
            else {
                conn.close();
                ptmt.close();
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public void insertReceiveGift(ReceiveGift receiveGift) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO ReceiveGifts (gift_name, post_id, quantity) " +
                "VALUES (?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, receiveGift.getGiftName());
            ptmt.setString(2, receiveGift.getPostId());
            ptmt.setInt(3, receiveGift.getQuantity());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void updateReceiveGift(ReceiveGift receiveGift) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "UPDATE TABLE ReceiveGifts SET gift_name = ?, post_id = ? WHERE quantity = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, receiveGift.getGiftName());
            ptmt.setString(2, receiveGift.getPostId());
            ptmt.setInt(3, receiveGift.getQuantity());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteReceiveGift(String postId, String giftName) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM ReceiveGifts WHERE gift_name = ? AND post_id = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, giftName);
            ptmt.setString(2, postId);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }
}
