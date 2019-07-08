import java.sql.*;

/**
 * stub implementation. use db-read-only-connection to get stats.
 * change to REST-API communication
 */
public class FramenetMonitor {

    /*from  w  w  w.  j  a va2  s.  c  om*/
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // ?
    // static final String DB_URL = "jdbc:mysql://localhost/EMP";
    static final String DB_URL = "jdbc:mysql://localhost/framenetdb";
    // static final String USER = "username";
    static final String USER = "gsw_ro";
    // static final String PASS = "password";
    static final String PASS = "GeschweigeDenn";

    public static void main(String[] args) throws Exception {
        // write_networking(null, null, null);

        show_fn_entry();
    }

    public static void show_fn_entry() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            // String sql = "SELECT id, first, last, age FROM Employees";
            String sql = "select distinct Entrystatus_Status from ENTRYSTATUS";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String first = rs.getString("first");
                String entrystatus_status = rs.getString("Entrystatus_Status");
                // String kb_in = rs.getString("kb_in");
                // String kb_out = rs.getString("kb_out");

//                System.out.print("ID: " + id);
//                System.out.print(", First: " + first);
                // System.out.printf("%d:\t%s\t%s\t%s\n", rs.getRow(), timestamp, kb_in, kb_out);
                System.out.println(entrystatus_status);

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

    public static void write_networking(String timestamp, String kb_in, String kb_out) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Insertint into database...");
        stmt = conn.createStatement();

        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        stmt = conn.createStatement();

        String sql = String.format("INSERT INTO networking (timestamp, kb_in, kb_out) VALUES ('%s', '%s', '%s')", timestamp, kb_in, kb_out);
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }
}
