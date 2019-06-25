import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Demo {

    public static void main (String[] args) {
        System.out.println("start system-monitoring");

        /**
         * implement system calls, i.e. vmstat, ifstat
         */
        String result = SystemMonitor.monitorNetworking(1, 5);
        System.out.println(result);
        System.exit(0);




        /**
         * implement ODBC connection
         */


    }
}
