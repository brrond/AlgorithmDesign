package logic;

import java.util.Iterator;

/**
 * Simple iterable class for linear feedback shift register
 */
public class LFSRIterable implements Iterable<Integer> {

    /**
     * Actual linear feedback shift register
     */
    private final LFSR lfsr;

    /**
     * Initial seed
     */
    private final int seed;

    /**
     * Basic constructor
     *
     * @param lfsr actual linear feedback shfit register
     * @param seed initial state
     */
    public LFSRIterable(LFSR lfsr, int seed) {
        this.lfsr = lfsr;
        this.seed = seed;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {

            private int curr = seed;

            @Override
            public boolean hasNext() {
                return lfsr.next(curr) != seed;
            }

            @Override
            public Integer next() {
                int ret = curr;
                curr = lfsr.next(curr);
                return ret;
            }
        };
    }
}
