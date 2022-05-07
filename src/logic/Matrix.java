package logic;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleSupplier;

public class Matrix {

    private Double[][] matrix;
    private final int N, M;

    private void checkSize(Matrix m2) {
        if(N != m2.N || M != m2.M) throw new IllegalArgumentException("m2 should have same size. Expected (" + N + ", " + M + ") got (" + m2.N + ", " + m2.M + ").");
    }

    private void checkSizeMul(Matrix m2) {
        if(M != m2.N) throw new IllegalArgumentException("(N, M) * (M, K) = (N, K)");
    }

    public static double mulVector(Double[] v1, Double[] v2) {
        if(v1 == null || v2 == null || v1.length != v2.length) throw new IllegalArgumentException("v1.length != v2.length");
        double d = 0.0;
        for(int i = 0; i < v1.length; i++) {
            d += v1[i] * v2[i];
        }
        return d;
    }

    public static Matrix identity(int n) {
        Matrix m = new Matrix(n, n);
        for(int i = 0; i < n; i++) {
            m.matrix[i][i] = 1.;
        }
        return m;
    }

    public static Matrix fromCoefficient(int n, int C) {
        Matrix F = new Matrix(n, n);
        for(int shift = 0; shift < n; shift++) {
            F.matrix[0][shift] = (double) (C >> (n - shift - 1) & 1);
            if(shift == 0) continue;
            F.matrix[shift][shift - 1] = 1.;
        }
        return F;
    }

    public static Matrix ones(int n, int m) {
        Matrix a = new Matrix(n ,m);
        a.forEach(() -> 1.);
        return a;
    }

    public Matrix(int n, int m) {
        N = n;
        M = m;

        matrix = new Double[N][M];
        forEach(() -> 0.0);
    }

    public Matrix(Double[][] mat) {
        N = mat.length;
        M = mat[0].length;
        matrix = new Double[N][M];
        for(int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, matrix[i], 0, mat[i].length);
        }
    }

    public void set(int i, int j, Double val) {
        matrix[i][j] = val;
    }

    public Double get(int i, int j) {
        return matrix[i][j];
    }

    public Double[] getRow(int i) {
        return matrix[i];
    }

    public Double[] getColumn(int j) {
        Double[] column = new Double[N];
        for(int i = 0; i < N; i++) {
            column[i] = matrix[i][j];
        }
        return column;
    }

    public void setRow(int i, Double[] row) {
        System.arraycopy(row, 0, matrix[i], 0, row.length);
    }

    public void setColumn(int j, Double[] column) {
        for(int i = 0; i < column.length; i++) {
            matrix[i][j] = column[i];
        }
    }

    public int getN() {return N;}

    public int getM() {return M;}

    public Matrix add(Matrix m2) {
        checkSize(m2);
        Matrix C = new Matrix(N, M);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                C.matrix[i][j] = matrix[i][j] + m2.matrix[i][j];
            }
        }
        return C;
    }

    public Matrix sub(Matrix m2) {
        return add(m2.mulM1());
    }

    public Matrix mul(Matrix m2) {
        checkSizeMul(m2);
        Matrix c = new Matrix(N, m2.M);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < m2.M; j++) {
                c.matrix[i][j] = mulVector(getRow(i), m2.getColumn(j));
            }
        }
        return c;
    }

    public Matrix mulM1() {
        return mul(-1.);
    }

    public Matrix mul(Double scalar) {
        Matrix B = new Matrix(N, M);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                B.matrix[i][j] = matrix[i][j] * scalar;
            }
        }
        return B;
    }

    public Matrix pow(Integer power) {
        if(power <= 0) throw new IllegalArgumentException("power <= 0");
        Matrix m = copy();
        for(int i = 0; i < power; i++) {
            m = m.mul(this);
        }
        return m;
    }

    public Matrix T() {
        Matrix t = new Matrix(M, N);
        for(int i = 0; i < N; i++) {
            t.setColumn(i, getRow(i));
        }
        return t;
    }

    public Matrix copy() {
        Matrix b = new Matrix(N, M);
        for(int i = 0; i < N; i++) {
            System.arraycopy(matrix[i], 0, b.matrix[i], 0, M);
        }
        return b;
    }

    public Matrix round(int precision) {
        Matrix b = copy();
        double p = Math.pow(10, precision);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                b.matrix[i][j] = Math.round(matrix[i][j] * p) / p;
            }
        }
        return b;
    }

    public Matrix round() {
        return round(0);
    }

    public double determinant() {
        if(N != M) throw new IllegalArgumentException("N != M");
        if(N == 1) return matrix[0][0];
        double res = 0.0;
        for(int i = 0; i < N; i++) {
            res += Math.pow(-1, 2 + i) * matrix[0][i] * cofactor(0, i).determinant();
        }
        return res;
    }

    public Matrix cofactor(int i, int j) {
        Matrix b = new Matrix(N - 1, M - 1);
        int pos1 = 0, pos2 = 0;
        for (int k = 0; k < N; k++) {
            if(k == i) continue;
            for (int l = 0; l < M; l++) {
                if (l != j) {
                    b.matrix[pos1][pos2++] = matrix[k][l];
                }
            }
            pos1++;
            pos2 = 0;
        }
        return b;
    }

    public void forEach(DoubleConsumer consumer) {
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                consumer.accept(matrix[i][j]);
            }
        }
    }

    public void forEach(DoubleSupplier supplier) {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                matrix[i][j] = supplier.getAsDouble();
            }
        }
    }

    public void forEach(DoubleFunction<Double> function) {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                matrix[i][j] = function.apply(matrix[i][j]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                stringBuilder.append(matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return N == matrix1.N && M == matrix1.M && Arrays.deepEquals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(N, M);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

}
