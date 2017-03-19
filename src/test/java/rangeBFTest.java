import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by mustafa on 3/18/17.
 */
public class rangeBFTest {

    @Test
    public void test() {
        rangeBF r = new rangeBF(0.1);

        r.insertValue(34254546);
        r.insertValue(34546);
        assertTrue(r.existsInRange(0, 44444));
        assertFalse(r.existsInRange(34254547, 34254549));
        assertTrue(r.existsInRange(0, 2 << 30 ));
    }

    @Test
    public void test2() {
        rangeBF r = new rangeBF(0.01);

        r.insertValue(7);

        assertFalse(r.existsInRange(0,6));
    }
}
