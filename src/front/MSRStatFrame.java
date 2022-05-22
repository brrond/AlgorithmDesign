package front;

import logic.MSR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MSRStatFrame {

    private JButton closeButton;
    private JLabel lMx;
    private JLabel lDx;
    private JPanel mainPanel;

    private MSR msr;

    private final MSRStatFrameInner msrStatFrameInner;

    private class MSRStatFrameInner extends FrameBase {

        public MSRStatFrameInner() {
            super("MSR Stat frame", 400, 600, null);
        }

        @Override
        protected void setupUI() {
            lMx.setText("M[x] : " + msr.getMx());
            lDx.setText("D[x] : " + msr.getDx());
        }

        @Override
        protected void setupButtons() {
            closeButton.addActionListener(e -> dispose());
        }

        @Override
        protected void setupMenu() {

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setFont(getFontToUse());

            final int WIDTH = 400;
            final int HEIGHT = 400;

            // paint autocorrelation function
            g.setColor(Color.cyan);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // draw axis
            g.setColor(Color.black);
            g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
            g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

            // draw function
            g.setColor(Color.red);
            g.drawLine(WIDTH / 2, (int) (1. / 3 * HEIGHT), (int) (1. / 4 * WIDTH), HEIGHT / 2);
            g.drawLine(WIDTH / 2, (int) (1. / 3 * HEIGHT), (int) (3. / 4 * WIDTH), HEIGHT / 2);

            // draw axis comments
            g.setColor(Color.black);
            g.drawLine((int) (1. / 4 * WIDTH), HEIGHT / 2 - 5, (int) (1. / 4 * WIDTH), HEIGHT / 2 + 5);
            g.drawLine((int) (3. / 4 * WIDTH), HEIGHT / 2 - 5, (int) (3. / 4 * WIDTH), HEIGHT / 2 + 5);
            g.drawLine(WIDTH / 2 - 5, (int) (1. / 3 * HEIGHT), WIDTH / 2 + 5, (int) (1. / 3 * HEIGHT));

            // draw comments
            g.drawString(String.valueOf((-1) * msr.getT()), (int) ((1. / 4 * WIDTH) - 15), HEIGHT / 2 + 20);
            g.drawString(String.valueOf(msr.getT()), (int) ((3. / 4 * WIDTH) - 15), HEIGHT / 2 + 20);
            g.drawString("-1", WIDTH / 2 + 20, (int) (1. / 3 * HEIGHT));

            // draw the rest of the function
            g.setColor(Color.red);
            g.drawLine(0, HEIGHT / 2, (int) (1. / 4 * WIDTH), HEIGHT / 2);
            g.drawLine((int) (3. / 4 * WIDTH), HEIGHT / 2, WIDTH, HEIGHT / 2);
        }
    }

    public MSRStatFrame(MSR msr) {
        this.msr = msr;

        msrStatFrameInner = new MSRStatFrameInner();
        msrStatFrameInner.setContentPane(mainPanel);
        msrStatFrameInner.setFonts();
    }
}
