import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * reads distinct status types and their count each day of the week.
 * uses db-read-only-connection to get stats.
 * TODO: consider changing to REST-API communication.
 */
public class FramenetMonitor {

    /*from  www.java2s.com*/
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // ?
    // static final String DB_URL = "jdbc:mysql://localhost/EMP";
    static final String DB_URL_GER_FN_CC = "jdbc:mysql://localhost/ger_fn_cc";
    static final String DB_URL_GSW_MONITORING = "jdbc:mysql://localhost/gsw_monitoring";
    // static final String USER = "username";
    static final String USER = "gsw_ro";
    // static final String PASS = "password";
    static final String PASS = "GeschweigeDenn";

    static class FrameNetEntryResult {
        /* Entry_Status Dictionary from HHUFrameNet de.hhu.framenet.backend.servlets FrameIndexServlet.java
        1 = "In Bearbeitung";
        2 = "Bearbeitung abgeschlossen";
        3 = "Bearbeitung bestätigt/Final";
        4 = "Es ist kompliziert...";
        5 = "Es ist immer noch kompliziert...";
        */

        String date;
        String time;
        String status_type; // see dictionary above
        String status_count; // number of observed status

        public FrameNetEntryResult(String date, String time, String status_type, String status_count) {
            this.date = date;
            this.time = time;
            this.status_type = status_type;
            this.status_count = status_count;
        }
    }

    public static void main(String[] args) throws Exception {
        monitor_framenet_entry_status();
    }


    /**
     * total count of entry-status.
     *
     * @throws Exception
     */
    public static ArrayList<FrameNetEntryResult> monitor_framenet_entry_status() throws Exception {
        ArrayList<FrameNetEntryResult> framenet_results = new ArrayList<FrameNetEntryResult>();

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myDateFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //
        DateTimeFormatter myTimeFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String date = myDateObj.format(myDateFormatObj);
        String time = myDateObj.format(myTimeFormatObj);

        Connection conn = null;
        Statement stmt = null;

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL_GER_FN_CC, USER, PASS);
        stmt = conn.createStatement();

        String sql = "select count(Entry_ID) as status_count, Status from fn_correction_status group by Status;";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String status_count = rs.getString("status_count");
            String Status = rs.getString("Status");

            // System.out.printf("%d:\t%s\t%s\t%s\t%s\n", rs.getRow(), date, time, status_count, Status);
            FrameNetEntryResult tmp = new FrameNetEntryResult(date, time, Status, status_count);
            framenet_results.add(tmp);
        }
        rs.close();
        stmt.close();
        conn.close();

        return framenet_results;
    }

}
