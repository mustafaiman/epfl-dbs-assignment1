import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by mustafa on 3/18/17.
 */
public class task3 {

    private static double D_P = 0.1;
    private static final int N_IP = 400000;

    public static void main(String[] args) throws URISyntaxException, IOException {

        if (args.length > 0)
            D_P = Float.parseFloat(args[0]);

        Scanner queryScanner = new Scanner(new File("task3_queries.txt"));

        BufferedReader streamIS = new BufferedReader(new FileReader(new File("file1.tsv")));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("out3.txt")));

        rangeBF rbf = new rangeBF(D_P, N_IP, 20, 33);

        int eid = 0;
        String line;
        int lip, rip;
        while ( (line = streamIS.readLine()) != null) {
            if (eid % 1000000 == 0) {
                System.out.println(eid);
            }
            rbf.insertValue(to32BitIP(line.split("\\s")[0]));
            eid++;
        }

        while (queryScanner.hasNext()) {
            lip = to32BitIP(queryScanner.next());
            rip = to32BitIP(queryScanner.next());
            if (rbf.existsInRange(lip, rip)) {
                writer.write("true,");
            } else {
                writer.write("false,");
            }
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
