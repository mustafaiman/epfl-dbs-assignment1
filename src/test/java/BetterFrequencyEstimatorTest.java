import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 3/12/17.
 */
public class BetterFrequencyEstimatorTest {

    @Test
    public void test() throws InsufficientMemoryException {
        BetterFrequencyEstimator estimator = new BetterFrequencyEstimator(10000000, 0.01f, 0.0001f, 0.9f);

        HashMap<Integer, Integer> realCounts = new HashMap<Integer, Integer>();

        ArrayList<Integer> randomIps = new ArrayList<Integer>();

        Random generator = new Random();
        for (int i = 0; i < 100000; i++) {
            int rnd = generator.nextInt(45);
            assert rnd >= 0;
            randomIps.add(rnd);
            Integer val = realCounts.get(rnd);

            if (val == null) {
                realCounts.put(rnd, 1);
            } else {
                realCounts.put(rnd, val+1);
            }
        }

        for (Integer integer : randomIps) {
            estimator.addArrival(integer);
        }

        for (HashMap.Entry<Integer, Integer> ent : realCounts.entrySet()) {
            System.out.println("Query: " + ent.getKey() + ", real: " + ent.getValue() + ", estimate: " + estimator.getFreqEstimation(ent.getKey()));
        }

        for (HashMap.Entry<Integer, Integer> ent : realCounts.entrySet()) {
            assertEquals((int)ent.getValue(), estimator.getFreqEstimation(ent.getKey()));
        }
    }


}
