package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatFrame {

    private JButton closeButton;
    private JLabel lMx;
    private JLabel lDx;
    private JPanel mainPanel;

    private int T;
    private double Mx, Dx;

    private final StatFrameInner msrStatFrameInner;

    private class StatFrameInner extends FrameBase {

        public StatFrameInner() {
            super("Stat frame", 400, 600, null);
        }

        @Override
        protected void setupUI() {
            lMx.setText("M[x] : " + Mx);
            lDx.setText("D[x] : " + Dx);
        }

        @Override
        protected void setupButtons() {
            closeButton.addActionListener(e -> dispose());
        }

        @Override
        protected void setupMenu() {
            setJMenuBar(JMenuBarFactory.createJMenuBar(new String[]{"File"},
                    new String[][]{{"Save", "Exit"}},
                    new ActionListener[][]{
                            new ActionListener[] {
                                    e -> savePNG(400, 400),
                                    e -> dispose()}
                    }));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setFont(getFontToUse());
            g.translate(0, menuSkip);

            final int WIDTH = 400;
            final int HEIGHT = 400;
            final int PICKS_COUNT = 4;

            // paint autocorrelation function
            g.setColor(Color.cyan);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // draw axis
            g.setColor(Color.black);
            g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
            g.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

            for(double i = 1.; i <= PICKS_COUNT; i++) {
                // draw function
                g.setColor(Color.red);
                g.drawLine((int) (WIDTH * i / PICKS_COUNT), (int) (1. / 5 * HEIGHT),
                        (int) (WIDTH * i / PICKS_COUNT) - 5, HEIGHT / 2);
                g.drawLine((int) (WIDTH * i / PICKS_COUNT), (int) (1. / 5 * HEIGHT),
                        (int) (WIDTH * i / PICKS_COUNT) + 5, HEIGHT / 2);

                // draw axis stick
                g.setColor(Color.black);
                g.drawLine((int) (WIDTH * i / PICKS_COUNT) - 5, HEIGHT / 2 - 5,
                        (int) (WIDTH * i / PICKS_COUNT) - 5, HEIGHT / 2 + 5);
                g.drawLine((int) (WIDTH * i / PICKS_COUNT) + 5, HEIGHT / 2 - 5,
                        (int) (WIDTH * i / PICKS_COUNT) + 5, HEIGHT / 2 + 5);

                // draw the rest of the function
                g.setColor(Color.red);
                g.drawLine((int) (WIDTH * (i - 1) / PICKS_COUNT) + 5, HEIGHT / 2,
                        (int) (WIDTH * i / PICKS_COUNT) - 5, HEIGHT / 2);
            }

            g.setColor(Color.black);
            for(int i = -PICKS_COUNT / 2 + 1, j = 1; i < PICKS_COUNT / 2; i++, j++) {
                g.drawString(String.valueOf(T * i), (int) (WIDTH * (double)j / PICKS_COUNT), HEIGHT / 2 + 20);
            }

            // draw axis stick for y=1
            g.drawLine(WIDTH / 2 - 5, (int) (1. / 5 * HEIGHT), WIDTH / 2 + 5, (int) (1. / 5 * HEIGHT));

            // draw comments for y = 1
            g.drawString("1", WIDTH / 2 + 20, (int) (1. / 5 * HEIGHT));
        }
    }

    public StatFrame(int T, double Mx, double Dx) {
        this.T = T;
        this.Mx = Mx;
        this.Dx = Dx;

        msrStatFrameInner = new StatFrameInner();
        msrStatFrameInner.setContentPane(mainPanel);
        msrStatFrameInner.setFonts();
    }
}
