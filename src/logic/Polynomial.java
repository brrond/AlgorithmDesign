package logic;

import java.io.Serializable;

public class Polynomial implements Serializable {
    private int N;
    private String num;
    private String coefficient;

    public Polynomial(int n, String num, String coefficient) {
        N = n;
        this.num = num;
        this.coefficient = coefficient;
    }

    public Polynomial(String[] polynomial) {
        if(polynomial == null || polynomial.length != 3) throw new IllegalArgumentException();
        N = Integer.parseInt(polynomial[0]);
        num = polynomial[1];
        coefficient = polynomial[2];
    }

    public Polynomial(String polynomial) {
        this(polynomial.split(" "));
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        return "" + N + " " + num + " " + coefficient;
    }
}
