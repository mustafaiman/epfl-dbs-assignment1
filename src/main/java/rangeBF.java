import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.BitSet;

/**
 * Created by mustafa on 17.03.17.
 */
public class rangeBF {

    private BloomFilter[] tables;
    private BitSet[] basicSets;

    private int lowerLevelI;
    private int upperLevelI;

    private int absoluteUpperLimit;

    public rangeBF(double pr, int n, int upperLevelI, int absoluteUpperLimit) {
        this.upperLevelI = upperLevelI;
        this.absoluteUpperLimit = absoluteUpperLimit;

        tables = new BloomFilter[upperLevelI - lowerLevelI];
        for (int i = 0; i < upperLevelI; i++) {
            tables[i] = new BloomFilter(pr, n);
        }
        if (absoluteUpperLimit - upperLevelI > 0 ) {
            basicSets = new BitSet[absoluteUpperLimit-upperLevelI];
        } else {
            basicSets = null;
        }
        for (int i = 0; i < absoluteUpperLimit - upperLevelI; i++) {
            basicSets[i] = new BitSet((int)Math.pow(2, absoluteUpperLimit - upperLevelI - i - 1));
        }
    }

    protected void insertPair(int level, int key) {
        if (absoluteUpperLimit == level) {
            return;
        }
        if (level >= upperLevelI) {
            basicSets[level - upperLevelI].set(key);
        } else {
            tables[level].insertValue(key);
        }
        insertPair(level + 1, Integer.divideUnsigned(key, 2));
    }

    public void insertValue(int key) {
        insertPair(0, key);
    }

    public boolean existsInRange(int l, int r) {
        return hierarchicalExists(0, l, r+1);
    }

    public boolean hierarchicalExists(int level, int lval, int rval) {
        if (rval == lval)
            return false;
        if (rval - lval == 1) {
            if (level < upperLevelI) {
                return tables[level].getEstimation(lval);
            } else {
                return basicSets[level - upperLevelI].get(lval);
            }
        }
        if (Integer.remainderUnsigned(lval, 2) != 0) {
            return hierarchicalExists(level, lval, lval+1) || hierarchicalExists(level, lval + 1, rval);
        } else {
            return hierarchicalExists(level+1, Integer.divideUnsigned(lval, 2), Integer.divideUnsigned(rval, 2)) ||
                    hierarchicalExists(level, 2 * Integer.divideUnsigned(rval, 2), rval);
        }
    }
}
