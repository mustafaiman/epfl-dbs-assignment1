import java.util.BitSet;

/**
 * Created by mustafa on 3/18/17.
 */
public class BloomFilter {

    private final int numHashFunctions;
    private final int width;

    private final BitSet table;

    private final int[] a = {983, 997, 991, 977, 971, 947, 941, 881, 883, 2161, 2153, 2521, 304537, 305423, 305749, 326707, 356561, 363401, 203207, 205043, 221587, 231109, 371383, 378463};

    private final int[] b = {1234, 442, 1789, 59, 91156, 3486, 123, 684, 1, 333, 47011, 55, 431, 1999, 7, 99, 7295, 501, 8834, 521285, 23, 435, 860, 2682, 21};

    public BloomFilter(double pr, int n) {
        this((int)(Math.ceil(((double)n * Math.log(pr)) / Math.log(0.6185))), n);
    }

    public BloomFilter(int width, int n) {
        this.width = width;
        this.numHashFunctions = (int)Math.ceil(Math.log(2)*width/n);

        System.out.println("Bloom filter m = " + width + ", d = " + numHashFunctions);
        table = new BitSet(width);
    }

    public void insertValue(int key) {
        for (int i = 0; i < numHashFunctions; i++) {
            table.set(hash(i, key));
        }
    }

    public boolean getEstimation(int key) {
        for (int i = 0; i < numHashFunctions; i++) {
            if (!table.get(hash(i, key))) {
                return false;
            }
        }
        return true;
    }

    public static int estimateCost(double p, int n) {
        int width = (int)Math.ceil((n * Math.log(p)) / Math.log(0.6185));
        return width/8;
    }


    private int hash(int funcNum, int num) {
        long x = Long.remainderUnsigned(num, width);
        long k = Long.remainderUnsigned(a[funcNum], width);
        long m = Long.remainderUnsigned(b[funcNum], width);
        return (int)Long.remainderUnsigned(x * k + m, width);
    }
}
