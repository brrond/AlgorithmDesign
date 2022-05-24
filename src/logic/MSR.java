package logic;

import java.util.Arrays;

// Matrix shift register
public class MSR {

    private Matrix A, B;

    public MSR(Matrix A, Matrix B) {
        if(A.getN() != A.getM()) throw new IllegalArgumentException("A.N != A.M");
        if(B.getN() != B.getM()) throw new IllegalArgumentException("B.N != B.M");
        if(A.determinant() == 0 || B.determinant() == 0) throw new IllegalArgumentException("A & B should be non-singular");

        this.A = A;
        this.B = B;
    }

    public MSR(int N, int M, int Ca, int Cb) {
        this(Matrix.fromCoefficient(N, Ca), Matrix.fromCoefficient(M, Cb).T());
    }

    public Matrix next(Matrix state) {
        if(state.getN() != A.getN() || state.getM() != B.getN()) throw new IllegalArgumentException("state should be (" + A.getN() + ", " + B.getN() + ")" );
        Matrix beforeSum = A.mul(state).mul(B);
        beforeSum.forEach(v -> (v % 2));
        return beforeSum;
    }

    public int getT() {
        int Ta = (int) (Math.pow(2, A.getN()) - 1);
        int Tb = (int) (Math.pow(2, B.getM()) - 1);

        /*int ta = Ta, tb = Tb;
        while(tb != 0) {
            int tmp = ta;
            ta = tb;
            tb = tmp % tb;
        }*/

        return Ta * Tb;
    }

    public int getTActual() {
        Matrix seed = Matrix.ones(A.getN(), B.getM());
        Matrix curr = seed.copy();
        curr = next(curr);
        int T = 1;
        while(!curr.equals(seed)) {
            curr = next(curr);
            T++;
        }
        return T;
    }

    public int getTActualForRow(int row) {
        if(row < 0 || row >= A.getN()) throw new IllegalArgumentException("row is invalid");
        Matrix seed = Matrix.ones(A.getN(), B.getM());
        Matrix curr = seed.copy();
        curr = next(curr);
        int T = 1;
        while(!Arrays.equals(curr.getRow(row), seed.getRow(row))) {
            curr = next(curr);
            T++;
        }
        return T;
    }

    public int getTActualForColumn(int column) {
        if(column < 0 || column >= B.getN()) throw new IllegalArgumentException("column is invalid");
        Matrix seed = Matrix.ones(A.getN(), B.getM());
        Matrix curr = seed.copy();
        curr = next(curr);
        int T = 1;
        while(!Arrays.equals(curr.getColumn(column), seed.getColumn(column))) {
            curr = next(curr);
            T++;
        }
        return T;
    }

    public int getTOfElement(int i, int j) {
        if(i < 0 || i >= A.getN() || j < 0 || j >= B.getN()) throw new IllegalArgumentException("position is invalid");
        return Math.min(getTActualForRow(i), getTActualForColumn(j));
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

    public int getN() { return A.getN(); }

    public int getM() { return B.getN(); }

}
