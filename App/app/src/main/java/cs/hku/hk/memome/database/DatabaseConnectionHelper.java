package cs.hku.hk.memome.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHelper {
    private static final String URL = "jdbc:mysql://sophia.cs.hku.hk/yfzou2";
    private static final String USER = "yfzou2";
    private static final String PASSWORD = "Ivan0729";

    private Connection conn;

    public void onConn() throws SQLException{
        DatabaseUtilities.openConnection(URL, USER, PASSWORD);
    }
}
