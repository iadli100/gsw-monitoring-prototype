import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * reads system-stats. uses ifstat, vmstat, etc. commands
 * TODO: implement disk usage / db transactions per day.
 */
public class SystemMonitor {

    static class NetworkingResult {
        String timestamp;
        String kb_in;
        String kb_out;

        public NetworkingResult(String timestamp, String kb_in, String kb_out) {
            this.timestamp = timestamp;
            this.kb_in = kb_in;
            this.kb_out = kb_out;
        }
    }

    /**
     * monitors traffic with 'seconds' delay 'repetitions' times.
     * TODO: implement min, max, median traffic over a day.
     * @param seconds
     * @param repetitions
     * @return
     */
    public static ArrayList<NetworkingResult> monitor_networking(int seconds, int repetitions) {
        ArrayList<NetworkingResult> networking_results = new ArrayList<NetworkingResult>();

        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            String command = String.format("ifstat -t -n %d %d", seconds, repetitions);
            p = r.exec(command);
            p.waitFor();


            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            // skip first two lines
            b.readLine();
            b.readLine();

            while ((line = b.readLine()) != null) {
                String[] lineSplit = line.split("\\s+");
                networking_results.add(new NetworkingResult(lineSplit[0], lineSplit[1], lineSplit[2]));
            }

            b.close();

            return networking_results;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return networking_results;
    }

}
