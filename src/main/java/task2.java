import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;


public class task2 {

    public static int D_AVAIL = 10000000;
    public static float D_PR1 = (float)0.1;
    public static float D_EPS = (float)0.01;
    public static float D_PR2 = (float)0.9;

    public static void main(String[] args) throws URISyntaxException, IOException, InsufficientMemoryException {


        if (args.length > 0)
            D_AVAIL = Integer.parseInt(args[0]);
        if (args.length > 1)
            D_PR1 = Float.parseFloat(args[1]);
        if (args.length > 2)
            D_EPS = Float.parseFloat(args[2]);
        if (args.length > 3)
            D_PR2 = Float.parseFloat(args[3]);



        Scanner queryScanner = new Scanner(new File(task2.class.getResource("/task2_queries.txt").toURI()));
        BufferedReader streamIS = new BufferedReader(new FileReader(new File(task1.class.getResource("/file1.tsv").toURI())));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out2.txt")));

        int eid = 0;

        BetterFrequencyEstimator fe = new BetterFrequencyEstimator(D_AVAIL, D_PR1, D_EPS, D_PR2);

        String line;
        int nextQueryTime = Integer.parseInt(queryScanner.next());
        int queryIP;
        while ( (line = streamIS.readLine()) != null) {
            if (nextQueryTime == eid) {
                String strip = queryScanner.next();
                queryIP = to32BitIP(strip);
                System.out.println(eid + " " + strip);
                writer.write(""+fe.getFreqEstimation(queryIP));
                if (queryScanner.hasNext()) {
                    writer.write(",");
                    nextQueryTime = Integer.parseInt(queryScanner.next());
                }
            }
            String queriedIp = line.split("\\s")[0];
            fe.addArrival(to32BitIP(queriedIp));
            eid++;
        }

        writer.close();
    }

    public static int to32BitIP(String str) {
        String[] parts = str.split("\\.");
        int[] p = new int[4];
        for (int i = 0; i < 4; i++) {
            p[i] = Integer.parseInt(parts[i]);
        }
        return (p[0] << 24) | (p[1] << 16) | (p[2] << 8) | p[3];
    }
}

