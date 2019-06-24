import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloWorld {

    public static void main (String[] args) throws IOException, InterruptedException {
        System.out.println("Hello IntelliJ!!");

        /**
         * implement system calls, i.e. vmstat, ifstat
         */
        Runtime r = Runtime.getRuntime();
        Process p = r.exec("ifstat 1 10");

        // Thread.sleep(2000);

        p.waitFor();

        BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";

        while ((line = b.readLine()) != null) {
            System.out.println(line);
        }

        b.close();

        /**
         * implement ODBC connection
         */


    }
}
