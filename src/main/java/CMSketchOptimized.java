import java.util.BitSet;

/**
 * Created by mustafa on 17.03.17.
 */
public class CMSketchOptimized {

    private final int numHashFunctions;
    private final int width;

    private final BitSet[] table;

    private final int[] a = {983, 997, 991, 977, 971, 947, 941, 881, 883};


    public CMSketchOptimized(double p, double eps) {
        this.width = (int)Math.ceil(Math.E/eps);
        this.numHashFunctions = (int)Math.log(1.0/p);

        System.out.println("CMSketch w = " + width +  ", d = " + numHashFunctions);


        table = new BitSet[numHashFunctions];
        for (int i = 0; i < numHashFunctions; i++) {
            table[i] = new BitSet(width);
        }
    }

    public void insertValue(int key) {
        for (int i = 0; i < numHashFunctions; i++) {
            table[i].set(hash(i, key));
        }
    }

    public boolean getEstimation(int key) {
        for (int i = 0; i < numHashFunctions; i++) {
            if (!table[i].get(hash(i, key))) {
                return false;
            }
        }
        return true;
    }


    private int hash(int funcNum, int num) {
        return Integer.remainderUnsigned(Integer.remainderUnsigned(num, width) * Integer.remainderUnsigned(a[funcNum], width), width);
    }

}
