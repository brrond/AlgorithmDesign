package front;

import logic.MSR;
import logic.Matrix;

import javax.swing.*;
import java.awt.*;

public class MSRShowFrame extends JFrame{
    private JPanel mainPanel;
    private JButton nextButton;
    private JButton closeButton;
    private JLabel lTa;
    private JLabel lTth;
    private JLabel lTb;
    private JLabel lTcalc;

    private final MSR msr;
    private Matrix curr;

    private void initParams() {
        lTth.setText(lTth.getText() + " " + msr.getT());
        lTcalc.setText(lTcalc.getText() + " " + msr.getTActual());
    }

    public MSRShowFrame(MSR msr, Matrix seed) {
        this.msr = msr;
        curr = seed.copy();

        setTitle("MSR Show");
        setSize(600, 900);
        setResizable(false);

        setContentPane(mainPanel);

        setVisible(true);
        closeButton.addActionListener(e -> dispose());
        nextButton.addActionListener(e -> {
            curr = msr.next(curr);
            repaint();
        });
        initParams();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int WIDTH = 600, HEIGHT = 600;
        int offset = 50;

        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        int xStep = (WIDTH - 2 * offset) / curr.getM();
        int yStep = (HEIGHT - 2 * offset) / curr.getN();

        g.setColor(Color.black);
        for(int i = 0; i < curr.getN(); i++) {
            for (int j = 0; j < curr.getM(); j++) {
                g.drawString(String.valueOf((int)(double) curr.get(i, j)), offset + j * xStep, offset + i * yStep);
            }
        }
    }

}
