package front;

import logic.MSR;
import logic.Matrix;

import javax.swing.*;

public class MSRFrame {
    private JPanel mainPanel;
    private JTextField tfN;
    private JTextField tfCa;
    private JTextField tfM;
    private JTextField tfCb;
    private JButton bSetSeed;
    private JButton bShow;
    private JButton bExit;

    private Matrix seed = null;

    private class MSRFrameInner extends FrameBase {
        public MSRFrameInner() {
            super("Something here", 400, 480, null);
        }

        @Override
        protected void setupUI() {

        }

        @Override
        protected void setupButtons() {
            bExit.addActionListener(e -> System.exit(0));

            bSetSeed.addActionListener(e -> {
                String sN = tfN.getText();
                String sM = tfM.getText();
                int N, M;
                try {
                    N = Integer.parseInt(sN);
                    M = Integer.parseInt(sM);
                } catch (NumberFormatException exception) {
                    handleException("N & M should be integers");
                    return;
                }

                if(N <= 0 || N > 10 || M <= 0 || M > 10) {
                    handleException("N & M <= 0 || N & M > 10");
                    return;
                }

                if(seed == null || seed.getN() != N || seed.getM() != M) seed = new Matrix(N, M);
                new MSRSetSeedFrame(seed);
            });

            bShow.addActionListener(e -> {
                String sN = tfN.getText();
                String sM = tfM.getText();
                String sCa = tfCa.getText();
                String sCb = tfCb.getText();
                int N, M, Ca, Cb;
                try {
                    N = Integer.parseInt(sN);
                    M = Integer.parseInt(sM);
                    Ca = Integer.parseInt(sCa, 2);
                    Cb = Integer.parseInt(sCb, 2);
                } catch(NumberFormatException exception) {
                    handleException("N, M, Ca, Cb should be integer");
                    return;
                }

                if((Ca & 1) == 0 || (Ca & N) == 0 || ((Ca & (1 << N)) == 0) || ((Cb & (1 << M)) == 0)) {
                    handleException("Ca[0] * Ca[N] != 1 || Cb[0] * Cb[N] != 1");
                    return;
                }

                if(seed == null) {
                    handleException("seed == null. Init seed first");
                    return;
                }

                try {
                    new MSRShowFrame(new MSR(Matrix.fromCoefficient(N, Ca), Matrix.fromCoefficient(M, Cb)), seed);
                } catch(IllegalArgumentException exception) {
                    handleException(exception.getMessage());
                } catch (Exception ignored) {

                }
            });
        }

        @Override
        protected void setupMenu() {

        }
    }

    private void tmpInit() {
        tfN.setText("3");
        tfCa.setText("1101");
        tfM.setText("4");
        tfCb.setText("11001");
    }

    public MSRFrame() {
        MSRFrameInner msrFrameInner = new MSRFrameInner();

        msrFrameInner.setContentPane(mainPanel);
        msrFrameInner.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        msrFrameInner.setFonts();

        tmpInit();
    }

}
