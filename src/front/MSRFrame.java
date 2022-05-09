package front;

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
            // TODO Create show window
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
