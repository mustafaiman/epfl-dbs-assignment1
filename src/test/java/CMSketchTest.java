import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mustafa on 17.03.17.
 */
public class CMSketchTest {

    @Test
    public void test() {
        CMSketch sk = new CMSketch(3, 200);

        sk.insertValue(4);
        sk.insertValue(4);
        sk.insertValue(1);
        sk.insertValue(8);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);


        assertEquals(2, sk.getEstimation(4));
        assertEquals(1, sk.getEstimation(1));
        assertEquals(1, sk.getEstimation(8));
        assertEquals(6, sk.getEstimation(123));
        assertEquals(0, sk.getEstimation(212443));
    }

    @Test
    public void testExistence() {
        CMSketchOptimized sk = new CMSketchOptimized(3, 200);

        sk.insertValue(4);
        sk.insertValue(4);
        sk.insertValue(1);
        sk.insertValue(8);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);
        sk.insertValue(123);


        assertTrue(sk.getEstimation(4));
        assertTrue(sk.getEstimation(4));
        assertTrue(sk.getEstimation(1));
        assertTrue(sk.getEstimation(8));
        assertTrue(sk.getEstimation(123));
        assertFalse(sk.getEstimation(212443));
    }
}
