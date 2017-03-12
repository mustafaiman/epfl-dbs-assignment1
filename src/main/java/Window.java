/**
 * Created by mustafa on 3/11/17.
 */
public class Window {
    private int endq;
    private int[] q;
    private final int ws;
    private final int wslen;
    private int internalAccumulator;

    private int internalStage;

    public Window(int ws, int wslen) {
        this.ws = ws;
        this.wslen = wslen;
        q = new int[this.ws];
    }

    public void increase(int seqId) {
        expireOld(seqId/wslen);
        internalAccumulator++;

    }

    public int getFreq(int seqId) {
        return getFreq(ws * wslen, seqId);
    }

    public int getFreq(int lastN, int seqId) {
        expireOld(seqId/wslen);

        lastN = lastN / wslen;
        int sum = 0;
        int partialEnd = (endq + ws - 1) % ws;
        while (lastN-- > 1) {
            sum += q[partialEnd];
            partialEnd = (partialEnd + ws - 1) % ws;
        }
        sum += q[partialEnd] / 2;
        return sum;
    }

    protected void expireOld(int stage) {
        while (internalStage < stage) {
            internalStage++;
            q[endq] = internalAccumulator;
            internalAccumulator = 0;
            endq = (endq + 1) % ws;
        }
    }

}
