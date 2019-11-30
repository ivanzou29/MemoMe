package cs.hku.hk.memome.database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtilities {

    private static final String URL = "jdbc:mysql://cdb-rhgud8sn.gz.tencentcdb.com:10029/MemoMe?useSSL=true";
    private static final String USER = "root";
    private static final String PASSWORD = "COMP3330@hku";

    public static Connection openConnection() {
        Connection conn = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void query (Connection conn, String sql) {

        if (conn == null) {
            return;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                System.out.println("id\t\t" + "name");
                while (!result.isAfterLast()) {
                    System.out.print(result.getString(idColumnIndex) + "\t\t");
                    System.out.println(result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
    }

    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }

        return execResult;
    }
}
