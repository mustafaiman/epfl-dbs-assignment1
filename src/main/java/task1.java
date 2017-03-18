import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by mustafa on 3/12/17.
 */
public class task1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Scanner queryScanner = new Scanner(new File("task1_queries.txt"));
        BufferedReader streamIS = new BufferedReader(new FileReader(new File("file1.tsv")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out1.txt")));

        int eid = 0;

        JumpingWindow jw = new JumpingWindow(10000, 0.01);

        String line;
        int nextQueryTime = Integer.parseInt(queryScanner.next());
        int queryIP;
        int queryRange;
        while ( (line = streamIS.readLine()) != null) {
            if (nextQueryTime == eid) {
                queryIP = Integer.parseInt(queryScanner.next().split("\\.")[0]);
                queryRange = Integer.parseInt(queryScanner.next());
                if (queryRange == 0) {
                    writer.write(""+jw.getFreqEstimation(queryIP));
                } else {
                    writer.write(""+jw.getFreqEstimation(queryIP, queryRange));
                }
                if (queryScanner.hasNext()) {
                    writer.write(",");
                    nextQueryTime = Integer.parseInt(queryScanner.next());
                }
            }
            String parts[] = line.split("\\.");
            jw.insertEvent(Integer.parseInt(parts[0]));
            eid++;
        }

        writer.close();
    }
}
