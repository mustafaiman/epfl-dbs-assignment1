import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 3/11/17.
 */
public class WindowTest {
    @Test
    public void countnothing() {
        Window window = new Window(3, 2);

        for (int i = 0; i < 6; i++) {
            window.step();
        }

        assertEquals(0, window.getFreq());
    }

    @Test
    public void count1in6() {
        Window window = new Window(3, 2);

        for (int i = 0; i < 6; i++) {
            if (i == 4) {
                window.increase();
            } else {
                window.step();
            }
        }

        assertEquals(1, window.getFreq());
    }

    @Test
    public void countApprox() {
        Window window = new Window(3, 2);

        // | 0 0 0
        window.increase();
        window.increase();
        // 2 | 0 0
        window.step();
        window.step();
        // 2 0 | 0
        window.increase();
        window.increase();
        // | 2 0 2
        window.increase();
        window.increase();
        // 2 | 0 2


        assertEquals(4, window.getFreq());
    }

    @Test
    public void countApprox2() {
        Window window = new Window(3, 2);

        // | 0 0 0
        window.step();
        window.step();
        // 0 | 0 0
        window.increase();
        window.increase();
        // 0 2 | 0
        window.increase();
        window.increase();
        // | 0 2 2
        window.increase();
        // (1) | 2 2


        assertEquals(4, window.getFreq());
    }

    @Test
    public void countApprox3() {
        Window window = new Window(3, 2);

        // | 0 0 0
        window.increase();
        window.increase();
        // 2 | 0 0
        window.increase();
        window.increase();
        // 2 2 | 0
        window.increase();
        window.increase();
        // | 2 2 2
        window.increase();
        window.increase();
        // 2 | 2 2
        window.increase();
        // 2 (1) | 2


        assertEquals(5, window.getFreq());
    }
}
