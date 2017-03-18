import java.util.BitSet;

/**
 * Created by mustafa on 3/12/17.
 */
public class BetterFrequencyEstimator {

    public final int MAX_DISTINCT = 400000;

    private final CMSketch cms;
    private final BloomFilter bloomFilter;

    public BetterFrequencyEstimator(int availableSpace, float pr1, float epsilon, float pr2) throws InsufficientMemoryException {

        if (availableSpace < CMSketch.estimateCost(1-pr2, epsilon) + BloomFilter.estimateCost(pr1, MAX_DISTINCT)) {
            System.out.println(CMSketch.estimateCost(1-pr2, epsilon) + BloomFilter.estimateCost(pr1, MAX_DISTINCT) );
            throw new InsufficientMemoryException();
        }

        cms = new CMSketch(1-pr2, epsilon);
        bloomFilter = new BloomFilter(pr1, MAX_DISTINCT);

    }

    void addArrival(int key) {
        bloomFilter.insertValue(key);
        cms.insertValue(key);
    }

    int getFreqEstimation(int key) {

        if (!bloomFilter.getEstimation(key)) {
            return 0;
        } else {
            return cms.getEstimation(key);
        }
    }
}

