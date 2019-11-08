package cs.hku.hk.memome.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHelper {
    private static final String URL = "jdbc:mysql://cdb-rhgud8sn.gz.tencentcdb.com:10029/MemoMe";
    private static final String USER = "root";
    private static final String PASSWORD = "COMP3330@hku";

    private Connection conn;

    public void onConn() throws SQLException{
        DatabaseUtilities.openConnection(URL, USER, PASSWORD);
    }
}
