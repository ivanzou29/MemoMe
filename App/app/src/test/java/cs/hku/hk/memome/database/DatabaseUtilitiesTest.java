package cs.hku.hk.memome.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseUtilitiesTest {

    private Connection conn;
    private static final String URL = "jdbc:mysql://cdb-rhgud8sn.gz.tencentcdb.com:10029/MemoMe";
    private static final String USER = "root";
    private static final String PASSWORD = "COMP3330@hku";

    @Test
    public void openConnection_ShouldNotReturnNullIfValid() {
        conn = DatabaseUtilities.openConnection(URL, USER, PASSWORD);
        System.out.println(conn);
    }

}
