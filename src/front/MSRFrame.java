package front;

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

    private void initUI() {

    }

    private void initFrame() {
        setSize(400, 480);
        setResizable(false);
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void initButtonListeners() {
        bExit.addActionListener(e -> {
            System.exit(0);
        });

        bSetSeed.addActionListener(e -> {
            // TODO Create setSeed window
        });
        bShow.addActionListener(e -> {
            // TODO Create show window
        });
    }

    public MSRFrame() {
        super();
        initFrame();
        initButtonListeners();

        // user code goes here
        tfN.setText("3");
        tfCa.setText("1101");
        tfM.setText("4");
        tfM.setText("11001");
    }
}
