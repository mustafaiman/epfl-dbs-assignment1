import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by mustafa on 17.03.17.
 */
public class rangeBF {

    private CMSketchOptimized[] tables;

    private int lowerLevelI;
    private int upperLevelI;

    private int cmsNumHash;
    private int cmsWidth;

    public rangeBF(double pr) {
        throw new NotImplementedException();
    }

    /**
     *
     * @param cmsNumHash
     * @param cmsWidth
     * @param upperLevelI Count-Min sketch that is responsible for the widest interval will be responsible for intervals
     *                    with 2^{@code upperLevelI} length.
     * @param lowerLevelI Count-Min sketch that is responsible for the narrowest interval will be responsible for
     *                    intervals with 2^{@code lowerLevelI} length.
     */
    public rangeBF(int cmsNumHash, int cmsWidth, int upperLevelI, int lowerLevelI) {
        this.cmsNumHash = cmsNumHash;
        this.cmsWidth = cmsWidth;
        this.upperLevelI = upperLevelI;
        this.lowerLevelI = lowerLevelI;

        tables = new CMSketchOptimized[upperLevelI - lowerLevelI + 1];
        for (int i = 0; i < upperLevelI - lowerLevelI; i++) {
            //TODO compute optimal numbers for CMSketchOptimized constructor
            tables[i] = new CMSketchOptimized(4, 200);
        }
    }

    public void insertValue(int key) {

    }

    public boolean existsInRange(int l, int r) {

    }
}
