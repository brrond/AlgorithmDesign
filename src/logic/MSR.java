package logic;

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

        int ta = Ta, tb = Tb;
        while(tb != 0) {
            int tmp = ta;
            ta = tb;
            tb = ta % tb;
        }

        return Ta * Tb / ta;
    }

    public int getTActual() {
        Matrix seed = Matrix.ones(A.getN(), B.getM());
        Matrix curr = seed.copy();
        int T = 0;
        while(curr != seed) {
            curr = next(curr);
            T++;
        }
        return T;
    }
}
