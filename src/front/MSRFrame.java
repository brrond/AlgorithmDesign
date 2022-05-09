package front;

import logic.MSR;
import logic.Matrix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MSRFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField tfN;
    private JTextField tfCa;
    private JTextField tfM;
    private JTextField tfCb;
    private JButton bSetSeed;
    private JButton bShow;
    private JButton bExit;

    private Matrix seed = null;

    private void initUI() {

    }

    private void tmpInit() {
        tfN.setText("3");
        tfCa.setText("1101");
        tfM.setText("4");
        tfCb.setText("11001");
    }

    private void initButtonListeners() {
        bExit.addActionListener(e -> System.exit(0));

        bSetSeed.addActionListener(e -> {
            String sN = tfN.getText();
            String sM = tfM.getText();
            int N, M;
            try {
                N = Integer.parseInt(sN);
                M = Integer.parseInt(sM);
            } catch (NumberFormatException exception) {
                // TODO Add error msg
                return;
            }

            if(N <= 0 || N > 10 || M <= 0 || M > 10) {
                // TODO Add error msg
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
                Ca = Integer.parseInt(sCa);
                Cb = Integer.parseInt(sCb);
            } catch(NumberFormatException exception) {
                // TODO HandleError
                return;
            }

            if(N <= 0 || M <= 0) {
                // TODO HandleError
                return;
            }

            if((Ca & 1) == 0 || (Ca & N) == 0)

            if(seed == null) {
                // TODO HandleError
                return;
            }

            try {
                new MSRShowFrame(new MSR(Matrix.fromCoefficient(N, Ca), Matrix.fromCoefficient(M, Cb)), seed);
            } catch(IllegalArgumentException exception) {
                // TODO Coefficients are wrong
            } catch (Exception exception) {
                // TODO Something went wrong
            }
        });
    }

    public MSRFrame() {
        super();

        setSize(400, 480);
        setResizable(false);
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
        
        initButtonListeners();

        tmpInit();
    }
}
