import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * reads system-stats. uses ifstat, vmstat, etc. commands
 */
public class SystemMonitor {

    public static String monitorNetworking(int seconds, int repetitions) {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            String command = String.format("ifstat -t -n %d %d", seconds, repetitions);
            p = r.exec(command);
            p.waitFor();


            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            String result = "";

            while ((line = b.readLine()) != null) {
                result += line + "\n";
            }

            b.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
