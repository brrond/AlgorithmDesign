package tests;

import logic.Matrix;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void mulVector() {
        assertEquals(32, Matrix.mulVector(new Double[]{1.,2.,3.}, new Double[]{4., 5., 6.}));

        for(int testIndex = 0; testIndex < 100; testIndex++) {
            Double[] A = new Double[testIndex + 1];
            Double[] B = new Double[testIndex + 1];

            Random random = new Random();
            double res = 0.0;
            for(int i = 0; i < testIndex + 1; i++) {
                double a = random.nextDouble();
                double b = random.nextDouble();
                A[i] = a;
                B[i] = b;
                res += a * b;
            }

            assertEquals(res, Matrix.mulVector(A, B));
        }
    }

    @Test
    void add() {
        Matrix C = new Matrix(new Double[][]{{0.0, 1.0}, {1.0, 0.0}}).add(new Matrix(new Double[][]{{1.0, 0.0}, {0.0, 1.0}}));
        assertEquals(new Matrix(new Double[][]{{1.0, 1.0}, {1.0, 1.0}}), C);

        for(int testIndex = 1; testIndex <= 100; testIndex++) {
            Matrix A = new Matrix(testIndex, testIndex + 5);
            Matrix B = new Matrix(testIndex, testIndex + 5);

            Matrix c = new Matrix(testIndex, testIndex + 5);
            Random random = new Random();
            for(int i = 0; i < testIndex; i++) {
                for(int j = 0; j < testIndex + 5; j++) {
                    double a = random.nextDouble();
                    double b = random.nextDouble();
                    A.set(i, j, a);
                    B.set(i, j, b);
                    c.set(i, j, a + b);
                }
            }

            assertEquals(c, A.add(B));
        }
    }

    @Test
    void sub() {
        Matrix C = new Matrix(new Double[][]{{0.0, 1.0}, {1.0, 0.0}}).sub(new Matrix(new Double[][]{{1.0, 0.0}, {0.0, 1.0}}));
        assertEquals(new Matrix(new Double[][]{{-1.0, 1.0}, {1.0, -1.0}}), C);

        for(int testIndex = 1; testIndex <= 100; testIndex++) {
            Matrix A = new Matrix(testIndex, testIndex + 5);
            Matrix B = new Matrix(testIndex, testIndex + 5);

            Matrix c = new Matrix(testIndex, testIndex + 5);
            Random random = new Random();
            for(int i = 0; i < testIndex; i++) {
                for(int j = 0; j < testIndex + 5; j++) {
                    double a = random.nextDouble();
                    double b = random.nextDouble();
                    A.set(i, j, a);
                    B.set(i, j, b);
                    c.set(i, j, a - b);
                }
            }

            assertEquals(c, A.sub(B));
        }
    }

    @Test
    void mulM1() {
        Random random = new Random();
        for(int i = 1; i < 100; i++) {
            Matrix a = new Matrix(i, i);
            Matrix b = new Matrix(i, i);
            for(int j = 0; j < i; j++) {
                for(int k = 0; k < i; k++) {
                    double c = random.nextDouble();
                    a.set(j, k, c);
                    b.set(j, k, -c);
                }
            }
            assertEquals(b, a.mulM1());
        }
    }

    @Test
    void testMul() {
        Random random = new Random();
        for(int i = 1; i < 100; i++) {
            Matrix a = new Matrix(i, i);
            Matrix b = new Matrix(i, i);
            for(int j = 0; j < i; j++) {
                for(int k = 0; k < i; k++) {
                    double c = random.nextDouble();
                    a.set(j, k, c);
                    b.set(j, k, c * 3.5);
                }
            }
            assertEquals(b, a.mul(3.5));
        }
    }

    @Test
    void mul() {
        Matrix A = new Matrix(new Double[][]{
                {-5.8, -8.9, 5.1, -1.2, -3.9},
                {8.9, 5.5, 0.7, 9.7, 0.5},
                {-1.2, 9.8, 4.4, -6.5, 9.3},
                {4.6, 6.8, 7.8, 3.3, -8.3},
                {-9.6, -8.5, -5.2, 4.3, -0.8}});

        Matrix B = new Matrix(new Double[][]{
                {8.1, 9.6, -2.0, 9.3, -3.4},
                {6.9, -3.5, 6.3, -1.8, 3.3},
                {3.7, -2.2, -4.0, -5.7, 6.0},
                {-6.4, -7.8, -9.4, -3.0, -3.3},
                {5.2, 0.1, -1.9, -8.6, 0.9}});

        Matrix C = new Matrix(new Double[][]{
                {-102.12, -26.78, -46.18, -29.85, 21.4},
                {53.15, -10.96, -78.08, 35.48, -39.47},
                {164.14, -3.87, 89.97, -114.36, 92.64},
                {48.76, -23.37, -12.81, 47.56, 35.24},
                {-187.33, -84.59, -52.45, -50.36, -41.52} });

        assertEquals(C, A.mul(B).round(2));
    }

    @Test
    void T() {
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            Matrix m1 = new Matrix(i, i);
            Matrix m2 = new Matrix(i, i);

            Double[] arr = new Double[i];
            for(int j = 0; j < i; j++) {
                arr[j] = random.nextDouble();
                m1.setRow(j, arr);
                m2.setColumn(j, arr);
            }

            assertEquals(m1, m2.T());
        }
    }

    @Test
    void determinant() {
        Matrix b = new Matrix(new Double[][]{
                {2., 4.},
                {3., 9.}
        });

        assertEquals(6, b.determinant());

        Matrix a = new Matrix(new Double[][]{
                {1., 2., 4.},
                {1., 3., 9.},
                {1., 4., 16.}
        });
        assertEquals(2, a.determinant());

        Matrix m = new Matrix(new Double[][]{
                {1., 2., 4., 8.},
                {1., 3., 9., 27.},
                {1., 4., 16., 64.},
                {1., 5., 25., 125.}});
        assertEquals(12, m.determinant());
    }

    @Test
    void identity() {
        Random random = new Random();
        for(int i = 1; i <= 100; i++) {
            Matrix a = new Matrix(i, i);
            for (int j = 0; j < i; j++) for (int k = 0; k < i; k++) a.set(j, k, random.nextDouble());
            Matrix I = Matrix.identity(i);
            assertEquals(a, a.mul(I));
        }
    }
}