import java.math.BigInteger;
import java.util.BitSet;

/**
 * Created by mustafa on 3/12/17.
 */
public class BetterFrequencyEstimator {

    public final int MAX_DISTINCT = 400000;

    private int table[][];
    private BitSet bitTable;


    private final int numHashFunctions;
    private final int numBitHashFunctions;
    private final int a[];
    private final int bitA[];
    private final int p;
    private final int bitP;


    public BetterFrequencyEstimator(int availableSpace, float pr1, float epsilon, float pr2) throws InsufficientMemoryException {
        bitP = (int)Math.floor(Math.log(pr1)/Math.log(0.6185) * MAX_DISTINCT);
        p = (int)(Math.E/epsilon);

        numHashFunctions = (int)(Math.log(1.0-pr2)/Math.log(0.5));
        numBitHashFunctions = (int)(Math.log(2)*bitP/MAX_DISTINCT);;

        bitTable = new BitSet(bitP);

        System.out.println("BitP, numBitHashFUnctions >>>>>>>>>>>>>>>> " + bitP + " " + numBitHashFunctions);
        System.out.println("P, numHashFUnctions >>>>>>>>>>>>>>>> " + p + " " + numHashFunctions);


        if (availableSpace < estimateSpace(bitP, 1, p, numHashFunctions)) {
            System.out.println(estimateSpace(bitP, 1, p, numHashFunctions));
            throw new InsufficientMemoryException();
        }
        table = new int[numHashFunctions][p];


        a = new int[]{983, 997, 991, 977, 971, 947, 941, 881, 883};
        bitA = new int[]{983, 997, 991, 977, 971, 947, 941, 881, 883};


    }

    int estimateSpace(int existenceWidth, int existenceHeight, int frequencyWidth, int frequencyHeight) {
        return existenceHeight * (existenceWidth/8) * 4 + frequencyHeight * frequencyWidth * 4;
    }

    void addArrival(int key) {

        for (int i = 0; i < numBitHashFunctions; i++) {
            bitTable.set(bitHash(i, key));
        }

        for (int i = 0; i < numHashFunctions; i++) {
            table[i][hash(i, key)]++;
        }
    }

    int getFreqEstimation(int key) {
        int minVal = Integer.MAX_VALUE;

        for (int i = 0; i < numBitHashFunctions; i++) {
            if (!bitTable.get(bitHash(i, key))) {
                return 0;
            }
        }

        for (int i = 0; i < numHashFunctions; i++) {
            if (minVal > table[i][hash(i, key)]) {
                minVal = table[i][hash(i, key)];
            }
        }
        return minVal;
    }

    private int hash(int funcNum, int num) {
        return Integer.remainderUnsigned(Integer.remainderUnsigned(num, p) * Integer.remainderUnsigned(a[funcNum], p), p);
    }

    private int bitHash(int funcNum, int num) {
        return Integer.remainderUnsigned(Integer.remainderUnsigned(num, bitP) * Integer.remainderUnsigned(bitA[funcNum], bitP), bitP);
    }
}

