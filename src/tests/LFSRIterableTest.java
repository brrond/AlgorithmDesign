package tests;

import logic.LFSR;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class LFSRIterableTest extends Assertions {

    private static LFSR lfsr1 = new LFSR(4, 25);
    private static Iterable<Integer> iterable = lfsr1.getIterable(10);

    @Test
    void iterable1() {
        Iterator<Integer> it = iterable.iterator();
        int curr = 10;
        while(it.hasNext()) {
            assertEquals(curr, it.next());
            curr = lfsr1.next(curr);
        }
    }

}