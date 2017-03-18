/**
 * Created by mustafa on 17.03.17.
 */
public class CMSketch {

    private final int numHashFunctions;
    private final int width;

    private final int table[][];

    private final int[] a = {983, 997, 991, 977, 971, 947, 941, 881, 883};


    public CMSketch(double p, double eps) {
        this.width = (int)Math.ceil(Math.E/eps);
        this.numHashFunctions = (int)Math.ceil(Math.log(1.0/p));

        System.out.println("CMSketch w = " + width +  ", d = " + numHashFunctions);

        table = new int[numHashFunctions][width];
    }

    public void insertValue(int key) {
        for (int i = 0; i < numHashFunctions; i++) {
            table[i][hash(i, key)]++;
        }
    }

    public int getEstimation(int key) {
        int minVal = Integer.MAX_VALUE;


        for (int i = 0; i < numHashFunctions; i++) {
            if (minVal > table[i][hash(i, key)]) {
                minVal = table[i][hash(i, key)];
            }
        }
        return minVal;
    }

    public static long estimateCost(double p, double eps) {
        int width = (int)Math.ceil(Math.E/eps);
        int numHashFunctions = (int)Math.ceil(Math.log(1.0/p));
        return width * 4 * numHashFunctions;
    }

    private int hash(int funcNum, int num) {
        return Integer.remainderUnsigned(Integer.remainderUnsigned(num, width) * Integer.remainderUnsigned(a[funcNum], width), width);
    }
}
