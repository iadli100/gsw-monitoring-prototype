import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * uses ODBC connection to store statistics
 */
public class OdbcConnector {

    /*from  www.java2s.com*/
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // ?
    // static final String DB_URL = "jdbc:mysql://localhost/EMP";
    static final String DB_URL_GSW_MONITORING = "jdbc:mysql://localhost/gsw_monitoring";
    // static final String USER = "username";
    static final String USER = "root";
    // static final String PASS = "password";
    static final String PASS = "hhu";

    public static void main(String[] args) throws Exception {
        // write_networking(null, null, null);
        show_networking();
    }

    public static void show_networking() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL_GSW_MONITORING, USER, PASS);
            stmt = conn.createStatement();
            // String sql = "SELECT id, first, last, age FROM Employees";
            String sql = "SELECT * FROM networking";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // int id = rs.getInt("id");
                String timestamp = rs.getString("timestamp");
                String kb_in = rs.getString("kb_in");
                String kb_out = rs.getString("kb_out");

                System.out.printf("%d:\t%s\t%s\t%s\n", rs.getRow(), timestamp, kb_in, kb_out);

            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * writes monitoring results into gsw_monitoring database for analysis with 'R'
     * @param timestamp
     * @param kb_in
     * @param kb_out
     * @throws Exception
     */
    public static void write_networking(String timestamp, String kb_in, String kb_out) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL_GSW_MONITORING, USER, PASS);
        System.out.println("Inserting into gsw_monitoring database...");
        stmt = conn.createStatement();

        conn = DriverManager.getConnection(DB_URL_GSW_MONITORING, USER, PASS);

        stmt = conn.createStatement();

        String sql = String.format("INSERT INTO networking (timestamp, kb_in, kb_out) VALUES ('%s', '%s', '%s')", timestamp, kb_in, kb_out);
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    /**
     * writes monitoring results into gsw_monitoring database for analysis with 'R'
     *
     * @throws Exception
     */
    public static void write_framenet_stats(ArrayList<FramenetMonitor.FrameNetEntryResult> fn_results) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL_GSW_MONITORING, USER, PASS);
        System.out.println("Inserting into gsw_monitoring database...");
        stmt = conn.createStatement();

        for (FramenetMonitor.FrameNetEntryResult fn_res : fn_results) {
            String sql = String.format("insert into framenet (date, time, status_type, status_count) values ('%s', '%s', '%s', '%s')",
                    fn_res.date, fn_res.time, fn_res.status_type, fn_res.status_count);
            stmt.executeUpdate(sql);
        }

        stmt.close();
        conn.close();
    }


}
