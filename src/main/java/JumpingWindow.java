/**
 * Created by mustafa on 3/11/17.
 */
public class JumpingWindow {

    private int windowSizeW;
    private double epsilon;
    private int windowSizeSW;
    private Window[] windows;
    private int eventCounter;

    public JumpingWindow(int windowSizeW, double epsilon) {
        this.windowSizeW = windowSizeW;
        this.epsilon = epsilon;
        this.windowSizeSW = (int)(2*epsilon*windowSizeW);
        this.windows = new Window[256];
        for (int i = 0; i < 256; i++) {
            this.windows[i] = new Window(windowSizeW/windowSizeSW, windowSizeSW);
        }
    }

    private int getAClass(int ip) {
        return ip;
    }

    void insertEvent(int srcIp) {
        int aclassip = getAClass(srcIp);
        assert aclassip < 256;
        this.windows[aclassip].increase(eventCounter++);
    }

    int getFreqEstimation(int srcIP) {
        int aclassip = getAClass(srcIP);
        return this.windows[aclassip].getFreq(eventCounter);
    }

    int getFreqEstimation(int srcIP, int queryWindowSizeW1) {
        int aclassip = getAClass(srcIP);
        return this.windows[aclassip].getFreq(queryWindowSizeW1, eventCounter);
    }
}
