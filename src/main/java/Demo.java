import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) throws Exception {
        System.out.println("start system-monitoring");

        // System.out.println("gathering system-info");
        /**
         * implement system calls, i.e. vmstat, ifstat
         */
        // System.out.println("listing networking-data: ");
        // ArrayList<SystemMonitor.NetworkingResult> networkingResults = SystemMonitor.monitor_networking(1, 5);

        /**
         * implement tool-specific statistics, i.e. framenet-entries & status
         */
        System.out.println("listing framenet-data: ");
        ArrayList<FramenetMonitor.FrameNetEntryResult> framenet_results = FramenetMonitor.monitor_framenet_entry_status();
        System.out.println();

        /**
         * implement ODBC connection
         */
        System.out.println("writing data to db");
        OdbcConnector.write_framenet_stats(framenet_results);
        // for (SystemMonitor.NetworkingResult netRes : networkingResults) {
            // System.out.printf("%s\t%s\t%s\n", netRes.timestamp, netRes.kb_in, netRes.kb_out);
            // OdbcConnector.write_networking(netRes.timestamp, netRes.kb_in, netRes.kb_out);
        // }


        System.out.println("finished.");
    }
}
