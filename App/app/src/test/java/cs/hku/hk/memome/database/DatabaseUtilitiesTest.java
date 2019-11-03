package cs.hku.hk.memome.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseUtilitiesTest {

    private Connection conn;
    private static final String URL = "jdbc:mysql://sophia.cs.hku.hk/yfzou2";
    private static final String USER = "yfzou2";
    private static final String PASSWORD = "Ivan0729";

    @Test
    public void openConnection_ShouldNotReturnNullIfValid() {
        conn = DatabaseUtilities.openConnection(URL, USER, PASSWORD);
        System.out.println(conn);
    }

}
