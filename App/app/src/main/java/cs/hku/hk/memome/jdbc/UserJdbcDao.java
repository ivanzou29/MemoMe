package cs.hku.hk.memome.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.UserDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.User;

public class UserJdbcDao implements UserDao {


    @Override
    public User getUserByEmail(String email) {

        Connection conn = DatabaseUtilities.openConnection();
        String sql = "SELECT * FROM Users WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setEmail(email);
                user.setCoin(rs.getInt("coin"));
                user.setPasscode(rs.getString("passcode"));
                user.setProfilePhoto(rs.getBytes("profile_photo"));
                user.setUsername(rs.getString("Username"));
                conn.close();
                ptmt.close();
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertUser(User user) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "INSERT INTO Users (email, coin, passcode, profile_photo, username) " +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, user.getEmail());
            ptmt.setInt(2, user.getCoin());
            ptmt.setString(3, user.getPasscode());
            ptmt.setBytes(4, user.getProfilePhoto());
            ptmt.setString(5, user.getUsername());
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteUserByEmail(String email) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "DELETE FROM Users WHERE email = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void updateCoinByEmailAndQuantity(String email, int quantity) {
        Connection conn = DatabaseUtilities.openConnection();
        String sql = "UPDATE TABLE Users SET coin = coin + ? WHERE email = ? ";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, quantity);
            ptmt.setString(2, email);
            ptmt.execute();
            conn.close();
            ptmt.close();
        } catch (SQLException e) {

        }
    }


}
