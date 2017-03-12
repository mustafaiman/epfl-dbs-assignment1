import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafa on 3/11/17.
 */
public class WindowTest {
    @Test
    public void countnothing() {
        Window window = new Window(3, 2);

        assertEquals(0, window.getFreq(6));
    }

    @Test
    public void count1in6() {
        Window window = new Window(3, 2);


        window.increase(4);


        assertEquals(1, window.getFreq(6));
    }

    @Test
    public void countApprox() {
        Window window = new Window(3, 2);


        window.increase(0);
        window.increase(1);
        window.increase(4);
        window.increase(5);
        window.increase(6);
        window.increase(7);


        assertEquals(4, window.getFreq(8));
    }

    @Test
    public void countApprox2() {
        Window window = new Window(3, 2);


        window.increase(2);
        window.increase(3);
        window.increase(4);
        window.increase(5);
        window.increase(6);

        assertEquals(4, window.getFreq(7));
    }

    @Test
    public void countApprox3() {
        Window window = new Window(3, 2);

        window.increase(0);
        window.increase(1);
        window.increase(2);
        window.increase(3);
        window.increase(4);
        window.increase(5);
        window.increase(6);
        window.increase(7);
        window.increase(8);


        assertEquals(5, window.getFreq(9));
    }
}
