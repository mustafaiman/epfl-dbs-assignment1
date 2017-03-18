import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by mustafa on 3/12/17.
 */
public class Task1SanityTest {

    private static Scanner queryScanner;
    private static BufferedReader streamScanner;
    private static File resFile = new File("real-results-1.txt");
    private static File outpfile = new File("out1.txt");
    private static final int DEFW = 10000;
    private static final double eps = 0.01;
    private static final int ABSOLUTE_ERRROR = (int)(DEFW*eps);

    private static ArrayList<Query> queries = new ArrayList<Query>();

    @BeforeClass
    public static void setup() throws URISyntaxException, IOException {
        if (resFile.exists()) {
            Scanner resScanner = new Scanner(resFile);
            while (resScanner.hasNext()) {
                Query query = new Query();
                query.result = Integer.parseInt(resScanner.next());
                queries.add(query);
            }
            resScanner.close();
        } else {
            queryScanner = new Scanner(new File("/task1_queries.txt"));
            String starter;
            int queryStart;
            int queryEnd;
            int queryIp;
            int queryW;
            while (queryScanner.hasNext()) {
                starter = queryScanner.next();
                queryEnd = Integer.parseInt(starter);
                queryIp = Integer.parseInt(queryScanner.next().split("\\.")[0]);
                queryW = Integer.parseInt(queryScanner.next());
                if (queryW == 0) {
                    queryW = Task1SanityTest.DEFW;
                }
                queryStart = queryEnd - queryW;
                Query newQuery = new Query();
                newQuery.start = queryStart;
                newQuery.end = queryEnd;
                newQuery.ip = queryIp;
                queries.add(newQuery);
            }
            queryScanner.close();

            streamScanner = new BufferedReader(new FileReader(new File(task1.class.getResource("/file1.tsv").toURI())));

            String line;
            for (int i = 0; (line = streamScanner.readLine()) != null; i++) {
                int ip = Integer.parseInt(line.split("\\.")[0]);
                for (Query query : queries) {
                    query.count(ip, i);
                }
                if (i % 1000000 == 0) {
                    System.out.println(i);
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(resFile));
            for (Query query: queries) {
                writer.write(""+query.result + '\n');
            }
            writer.close();

        }

    }

    @Test
    public void test() throws FileNotFoundException {
        if (!outpfile.exists()) {
            throw new Error("Output file does not exists.");
        }
        Iterator<Query> queryIterator = queries.iterator();

        Scanner realScanner = new Scanner(outpfile);
        realScanner.useDelimiter(",");

        double minError = Double.MAX_VALUE;
        double maxError = 0;
        int q = 0;
        double sumError = 0;
        while (realScanner.hasNext()) {
            q++;
            int val = Integer.parseInt(realScanner.next());

            int real = queryIterator.next().result;

            double error = (Math.abs(val - real));

            maxError = Math.max(maxError, error);
            minError = Math.min(minError, error);
            sumError += error;

            System.out.println("" + val + ", " + real );

            if (error > ABSOLUTE_ERRROR) {
                throw new Error("Error is bigger than absolute!");
            }
        }
        if ( queryIterator.hasNext()) {
            throw new Error("There are not enough results!");
        }
        System.out.println("Max: " + maxError);
        System.out.println("Min: " + minError);
        System.out.println("Avg: " + (sumError / q));
    }


    static class Query {
        int start;
        int end;
        int ip;
        int result;

        void count(int ip, int seq) {
            if (seq < end && seq >= start && ip == this.ip) {
                result++;
            }
        }
    }
}
