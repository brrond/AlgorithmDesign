package front;

import logic.MSR;
import logic.Matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MSRShowFrame {
    private JPanel mainPanel;
    private JButton nextButton;
    private JButton closeButton;
    private JLabel lTth;
    private JLabel lTcalc;

    private JButton autoButton;
    private JTextField speedTextField;
    private JButton statButton;
    private JButton button2;
    private boolean turnedOn = false;
    private Timer timer = new Timer(0, e -> nextButton.doClick());

    private final MSRShowFrameInner msrShowFrameInner;

    private final MSR msr;
    private Matrix curr;

    private class MSRShowFrameInner extends FrameBase {

        public MSRShowFrameInner() {
            super("MSR show", 600, 900, null);
        }

        @Override
        protected void setupUI() {

        }

        @Override
        protected void setupButtons() {
            closeButton.addActionListener(e -> dispose());
            nextButton.addActionListener(e -> {
                curr = msr.next(curr);
                repaint();
            });
            autoButton.addActionListener(e -> toggleAuto());
            statButton.addActionListener(e -> new MSRStatFrame(msr.getTOfElement(msr.getN() - 1, msr.getM() - 1),
                                                                msr.getMx(),
                                                                msr.getDx()));
        }

        @Override
        protected void setupMenu() {
            setJMenuBar(JMenuBarFactory.createJMenuBar(new String[]{"File"},
                    new String[][]{{"Save", "Exit"}},
                    new ActionListener[][]{
                            new ActionListener[] {
                                    e -> savePNG(600, 600),
                                    e -> dispose()}
                    }));
        }

        public void paint(Graphics g) {
            super.paint(g);

            g.setFont(getFontToUse());
            g.translate(0, menuSkip); // adds menuBar skip

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

            g.drawRect((curr.getM() - 1) * xStep + offset / 2, (curr.getN() - 1) * yStep + offset / 2,
                     offset, offset);
        }

        public void handleException(String msg) {
            super.handleException(msg);
        }
    }

    private void toggleAuto() {
        int speed;
        try {
            speed = Integer.parseInt(speedTextField.getText());
        } catch(NumberFormatException e) {
            msrShowFrameInner.handleException("Speed should be Integer number");
            speed = 10;
        }

        double everyNSec = 60. / speed;

        if(turnedOn) {
            // turn off
            timer.stop();
            autoButton.setText("Start");
        } else {
            // think about it
            timer.setDelay((int) (everyNSec * 1000));
            timer.start();
            autoButton.setText("Stop");
        }

        turnedOn = !turnedOn;
    }

    private void initParams() {
        lTth.setText(lTth.getText() + " " + msr.getT());
        lTcalc.setText(lTcalc.getText() + " " + msr.getTActual());
    }

    public MSRShowFrame(MSR msr, Matrix seed) {
        this.msr = msr;
        curr = seed.copy();

        msrShowFrameInner = new MSRShowFrameInner();
        msrShowFrameInner.setContentPane(mainPanel);
        msrShowFrameInner.setFonts();

        initParams();
    }

}
