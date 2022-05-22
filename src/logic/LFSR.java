package logic;

/**
 * Basic linear feedback shift register
 * Uses no additional memory
 */
public class LFSR {

    /**
     * Initial state
     */
    private final int C;

    /**
     * Count of triggers
     */
    private final int n;

    /**
     * Constructor for linear-feedback shift register
     * with {@code n} triggers and {@code C} state
     *
     * @param n count of triggers
     * @param C coefficient for register
     */
    public LFSR(int n, int C) {
        if(n <= 0 || n > 32) throw new IllegalArgumentException("n <= 0 || n > 32");
        if(((C & (1 << n)) == 0) || ((C & 1) == 0)) throw new IllegalArgumentException("C0 * Cn != 1");
        //(C & 1) == 0 ||
        this.n = n;
        this.C = C;
    }

    /**
     * Method generate result of sum (XOR) of current state and {@code num}
     *
     * @param num current register state
     * @return XOR by coefficients
     */
    public int outputOfSum(int num) {
        int sum = (C & 1) & (num & 1);
        for(int i = 1; i < n; i++) sum ^= ((C >> i & 1) & (num >> i & 1));
        return sum;
    }

    /**
     * Method to generate next value from current state
     *
     * @param num current state of register
     * @return next state of register
     */
    public int next(int num) {
        if(num == 0) return 0;
        else if(num < 0) throw new IllegalArgumentException();
        int sum = outputOfSum(num);
        num = num >> 1;
        num |= (sum << (n - 1));
        return num;
    }

    /**
     * @return coefficient
     */
    public int getC() {
        return C;
    }

    /**
     * @return count of triggers
     */
    public int getN() {
        return n;
    }

    /**
     * @return theoretical period of linear feedback shift register
     */
    public int getT() { return (int) (Math.pow(2, n) - 1); }

    /**
     * @return actual period of linear feedback shift register
     */
    public int getTActual() {
        int T = 1;
        int curr = next(1);
        while(curr != 1) {
            curr = next(curr);
            T++;
        }
        return T;
    }

    /**
     * @return expected value of binary sequence
     */
    public double getMx() {
        return - 1. / getT();
    }

    /**
     * @return dispersion of binary sequence
     */
    public double getDx() {
        return 1. - Math.pow(getMx(), 2);
    }

    /**
     * Method to get basic integer iterable from curr {@code seed}
     *
     * @param seed basic state of triggers
     * @return {@code new LFSRIterable} to iterate through collection
     */
    public Iterable<Integer> getIterable(int seed) {
        return new LFSRIterable(this, seed);
    }
}
