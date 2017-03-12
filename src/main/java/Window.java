/**
 * Created by mustafa on 3/11/17.
 */
public class Window {
    private int endq;
    private int[] q;
    private int sum;
    private int half;
    private final int ws;
    private final int wslen;
    private int internalCounter;
    private int internalAccumulator;

    public Window(int ws, int wslen) {
        this.ws = ws-1;
        this.wslen = wslen;
        q = new int[this.ws];
    }

    protected void updateSummary(int newVal) {
        half = q[endq]/2;
        sum -= q[endq];
        q[endq] = newVal;
        sum += q[endq];
        endq = (endq + 1) % ws;
    }

    public void increase() {
        internalAccumulator++;
        step();

    }

    public void step() {
        internalCounter++;

        if (internalCounter == wslen) {
            updateSummary(internalAccumulator);
            internalAccumulator = 0;
            internalCounter = 0;
        }
    }

    public int getFreq() {
        return sum + half;
    }

    public int getFreq(int lastN) {
        lastN = lastN / wslen;
        if (lastN == ws) {
            return getFreq();
        } else {
            int sum = 0;
            int partialEnd = endq;
            while (lastN-- > 0) {
                sum += q[partialEnd];
                partialEnd = (partialEnd + ws - 1) % ws;
            }
            sum += q[partialEnd] / 2;
            return sum;
        }
    }

}
