package logic;

public class LFSR {
    // Linear-feedback shift register

    private final int C;
    private final int n;

    public LFSR(int n, int C) {
        if(n <= 0 || n > 32) throw new IllegalArgumentException();
        if(((C & (1 << n)) == 0)) throw new IllegalArgumentException();
        //(C & 1) == 0 ||
        this.n = n;
        this.C = C;
    }

    public int outputOfSum(int num) {
        int sum = (C & 1) & (num & 1);
        for(int i = 1; i < n; i++) sum ^= ((C >> i & 1) & (num >> i & 1));
        return sum;
    }

    public int next(int num) {
        if(num == 0) return 0;
        else if(num < 0) throw new IllegalArgumentException();
        int sum = outputOfSum(num);
        num = num >> 1;
        num |= (sum << (n - 1));
        return num;
    }

    public void printPeriod(int seed) {
        for(int i = 0; i < Math.pow(2, n) - 1; i++) {
            System.out.printf("%d) %s\n",
                    i, String.format("%" + n + "s", Integer.toBinaryString(seed)).replace(' ', '0'));
            seed = next(seed);
        }
    }

    public int getC() {
        return C;
    }

    public int getN() {
        return n;
    }
}
