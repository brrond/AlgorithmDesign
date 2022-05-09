import front.MSRFrame;

import javax.swing.*;

public class MainMSR {

    public static void main(String[] args) {
        /*MSR msr = new MSR(3, 4, 13, 25);
        Matrix s0 = new Matrix(new Double[][]{
                {1., 0., 1., 0.},
                {0., 1., 0., 1.},
                {1., 1., 1., 1.}
        });
        System.out.println(s0);
        System.out.println(msr.next(s0));
         */

        new MSRFrame();
    }

}
