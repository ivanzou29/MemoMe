package cs.hku.hk.memome.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs.hku.hk.memome.dao.UserDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.User;

public class UserJdbcDao implements UserDao {
    private static DatabaseUtilities databaseUtilities = new DatabaseUtilities();
    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM Users WHERE email = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
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
                ptmt.close();
                conn.close();
                return user;
            } else {
                ptmt.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertUser(User user) {

        String sql = "INSERT INTO Users (email, coin, passcode, profile_photo, username) " +
                "VALUES (?,?,?,?,?)";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, user.getEmail());
            ptmt.setInt(2, user.getCoin());
            ptmt.setString(3, user.getPasscode());
            ptmt.setBytes(4, user.getProfilePhoto());
            ptmt.setString(5, user.getUsername());
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void deleteUserByEmail(String email) {

        String sql = "DELETE FROM Users WHERE email = ?";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    @Override
    public void updateCoinByEmailAndQuantity(String email, int quantity) {

        String sql = "UPDATE Users SET coin = coin + ? WHERE email = ? ";
        try {
            Connection conn = databaseUtilities.openConnection();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, quantity);
            ptmt.setString(2, email);
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
