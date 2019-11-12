package cs.hku.hk.memome.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DatabaseUtilitiesTest {

    private Connection conn;

    @Test
    public void openConnection_ShouldNotReturnNullIfValid() {
        conn = DatabaseUtilities.openConnection();
        System.out.println(conn);
    }

}
