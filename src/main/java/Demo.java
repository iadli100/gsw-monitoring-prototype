import java.util.ArrayList;

public class Demo {

    public static void main(String[] args) throws Exception {
        System.out.println("start system-monitoring");

        System.out.println("gathering system-info");
        /**
         * implement system calls, i.e. vmstat, ifstat
         */
        ArrayList<SystemMonitor.NetworkingResult> networkingResults = SystemMonitor.monitor_networking(1, 5);


        System.out.println("writing data to db");
        /**
         * implement ODBC connection
         */
        for (SystemMonitor.NetworkingResult netRes : networkingResults) {
            System.out.printf("%s\t%s\t%s\n", netRes.timestamp, netRes.kb_in, netRes.kb_out);
            OdbcConnector.write_networking(netRes.timestamp, netRes.kb_in, netRes.kb_out);
        }


        System.out.println("finished.");
    }
}
