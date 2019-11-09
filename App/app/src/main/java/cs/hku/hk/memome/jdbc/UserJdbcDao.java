package cs.hku.hk.memome.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs.hku.hk.memome.dao.UserDao;
import cs.hku.hk.memome.database.DatabaseUtilities;
import cs.hku.hk.memome.model.User;

public class UserJdbcDao implements UserDao {


    @Override
    public User getUserFromEmail(String email) {

        Connection conn = DatabaseUtilities.openConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE email = \"" + email + "\"");
            rs.next();
            User user = new User();
            user.setEmail(email);
            user.setCoin(rs.getInt("coin"));
            user.setPasscode(rs.getString("passcode"));
            user.setProfilePhoto(rs.getBytes("profile_photo"));
            user.setUsername(rs.getString("Username"));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}
