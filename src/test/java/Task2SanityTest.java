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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by mustafa on 3/12/17.
 */
public class Task2SanityTest {
    private static Scanner queryScanner;
    private static BufferedReader streamScanner;
    private static File resFile = new File("real-results-2.txt");
    private static File outpfile = new File("out2.txt");
    private static File streamFile;


    private static final HashMap<String, Integer> freqs = new HashMap<String, Integer>();
    private static final ArrayList<Query> queries = new ArrayList<>();

    @BeforeClass
    public static void setup() throws URISyntaxException, IOException {
        streamFile = new File(Task2SanityTest.class.getResource("/task2_queries.txt").toURI());
        if (resFile.exists()) {
            Scanner resultsScanner = new Scanner(resFile);
            while (resultsScanner.hasNext()) {
                Query q = new Query();
                q.upperLimit = Integer.parseInt(resultsScanner.next());
                q.val = Integer.parseInt(resultsScanner.next());
                queries.add(q);
            }
            resultsScanner.close();
        } else {
            queryScanner = new Scanner(streamFile);

            while (queryScanner.hasNext()) {
                int lim = Integer.parseInt(queryScanner.next());
                String strIp = queryScanner.next();
                Query q = new Query();
                q.ip = strIp;
                q.upperLimit = lim;
                queries.add(q);
            }
            queryScanner.close();

            streamScanner = new BufferedReader(new FileReader(new File(task1.class.getResource("/file1.tsv").toURI())));
            BufferedWriter writer = new BufferedWriter(new FileWriter(resFile));

            String line;

            Iterator<Query> it = queries.iterator();

            Query current = it.next();

            for (int i = 0; (line = streamScanner.readLine()) != null; i++) {
                if (i == current.upperLimit) {
                    Integer xval = freqs.get(current.ip);
                    if (xval == null)
                        xval = 0;
                    writer.write("" + current.upperLimit + " " +xval + '\n');
                    current.val = xval;
                    if (it.hasNext())
                        current = it.next();
                }
                String ip = line.split("\\s")[0];
                Integer val = freqs.get(ip);

                if (val == null) {
                    freqs.put(ip, 1);
                } else {
                    freqs.put(ip, 1+val);
                }
                if (i % 1000000 == 0) {
                    System.out.println(i);
                }
            }


            writer.close();

        }

    }

    @Test
    public void test() throws FileNotFoundException {
        if (!outpfile.exists()) {
            throw new Error("Output file does not exists.");
        }
        Iterator<Query> queriesIterator = queries.iterator();

        Scanner realScanner = new Scanner(outpfile);
        realScanner.useDelimiter(",");


        int q = 0;
        int numErrorResult = 0;
        while (realScanner.hasNext()) {
            q++;
            int val = Integer.parseInt(realScanner.next());

            Query qery = queriesIterator.next();
            int real = qery.val;

            double error = (Math.abs(val - real));




            System.out.println("" + val + ", " + real + ", ±" + error);

            if (real == 0) {

            } else {
                if (error > qery.upperLimit*task2.D_EPS) {
                    numErrorResult++;
                }
            }
        }
        if ( queriesIterator.hasNext()) {
            throw new Error("There are not enough results!");
        }
        if (numErrorResult > q*(1.0-task2.D_PR2)) {
            throw new Error();
        }
    }

    static class Query {
        String ip;
        int upperLimit;
        int val;
    }
}
