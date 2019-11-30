package cs.hku.hk.memome.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import cs.hku.hk.memome.dao.OwnDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.Own;

public class OwnJdbcDao implements OwnDao {
    private static Connection conn = DatabaseUtilities.openConnection();
    @Override
    public Collection<Own> getOwnsByEmail(String email) {
        String sql = "SELECT * FROM Own WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            Collection<Own> owns = new ArrayList<Own>();
            while (rs.next()) {
                String giftName = rs.getString("gift_name");
                int quantity = rs.getInt("quantity");
                Own own = new Own(email, giftName, quantity);
                owns.add(own);
            }
            ptmt.close();
            return owns;
        } catch (SQLException e) {
            return new ArrayList<Own>();
        }
    }

    @Override
    public void insertGiftOwnership(Own own) {
        String sql = "INSERT INTO Own (email, gift_name, quantity) " +
                "VALUES (?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, own.getEmail());
            ptmt.setString(2, own.getGiftName());
            ptmt.setInt(3, own.getQuantity());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void updateGiftOwnership(Own own) {
        String sql = "UPDATE Own SET gift_name = ?, quantity = ? WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, own.getGiftName());
            ptmt.setInt(2, own.getQuantity());
            ptmt.setString(3, own.getEmail());
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteGiftOwnership(String email, String giftName) {
        String sql = "DELETE FROM Own WHERE email = ? AND gift_name = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.setString(2, giftName);
            ptmt.execute();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

}
