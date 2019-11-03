package cs.hku.hk.memome.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseConnectionHelperTest {

    private DatabaseConnectionHelper databaseConnectionHelper;


    @Test
    public void onConn_ShouldConnectToTheDatabaseSuccessfully() throws SQLException {
        databaseConnectionHelper = new DatabaseConnectionHelper();
        databaseConnectionHelper.onConn();
    }



}
