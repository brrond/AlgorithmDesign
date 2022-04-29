package front;

import logic.LFSR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LFSRBuilderFrame extends JFrame {

    private LFSR lfsr;
    private int seed;
    private int curr;

    // UserInterface elemtns
    JLabel lCurr10, lCurr2;
    JTextField tfCurr10, tfCurr2;
    JButton bNext, bClose;

    /**
     * Setups user interface
     */
    private void setupUI() {
        setFont(LFSRFrame.font);
        lCurr10 = new JLabel("Curr (10) : ");
        lCurr2 = new JLabel("Curr (2) : ");
        tfCurr10 = new JTextField();
        tfCurr2 = new JTextField();

        lCurr10.setFont(LFSRFrame.font);
        lCurr2.setFont(LFSRFrame.font);
        tfCurr10.setFont(LFSRFrame.font);
        tfCurr2.setFont(LFSRFrame.font);

        // debug elements
        JLabel[] debugs = new JLabel[12];
        for(JLabel label : debugs) {
            label = new JLabel("");
            label.setVisible(false);
            add(label);
        }

        add(lCurr10);
        add(tfCurr10);
        add(lCurr2);
        add(tfCurr2);
    }

    /**
     * Setups buttons and buttons listeners
     */
    private void setupButtons() {
        bNext = new JButton("Next");
        bNext.addActionListener(event -> {
            curr = lfsr.next(curr);
            repaint();

            tfCurr10.setText(String.valueOf(curr));
            tfCurr2.setText(String.format("%" + lfsr.getN() + "s", Integer.toBinaryString(curr)).replace(' ', '0'));
        });

        bClose = new JButton("Close");
        bClose.addActionListener(event -> dispose());

        add(bNext);
        add(bClose);
    }

    /**
     * Constructor
     *
     * @param lfsr linear forward shift register to work with
     * @param seed default init
     */
    public LFSRBuilderFrame(LFSR lfsr, int seed) { // TODO Create BuilderState to divide business logic from front
        super();

        this.lfsr = lfsr;
        this.seed = seed;
        this.curr = seed;

        setTitle("Tester");
        setResizable(false);
        setLayout(new GridLayout(9, 2));
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
                System.out.println("Done"); // TODO Remove this
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setupUI();
        tfCurr10.setText(String.valueOf(curr));
        tfCurr2.setText(String.format("%" + lfsr.getN() + "s", Integer.toBinaryString(curr)).replace(' ', '0'));
        setupButtons();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int WIDTH = 600;
        int HEIGHT = 400;
        int offset = 30;

        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw output of sum
        g.fillRect(offset / 2, HEIGHT / 2, offset, offset);

        g.setColor(Color.black);
        g.drawString(String.valueOf(lfsr.outputOfSum(curr)), offset / 3 * 2, HEIGHT / 2); // show output of sum
        g.drawLine(offset / 2, 355, 590, 355); // TODO Change to width height
        g.drawString("out", WIDTH - offset * 3 / 2, HEIGHT - offset * 5 / 6);

        // draw triggers
        for(int i = 0; i < lfsr.getN(); i++) {
            int j = 10 - i - 1;
            g.setColor(Color.cyan);
            g.fillRect(offset + (50 * j), HEIGHT - offset * 2, 30, 30);
            g.setColor(Color.black);
            g.drawRect(offset + (50 * j), HEIGHT - offset * 2, 30, 30);
        }

        // draw sum
        g.drawRect(WIDTH - offset * 2, offset * 2, offset, HEIGHT - 4 * offset);
        g.drawLine(WIDTH - offset * 2 + offset, offset * 2 + (HEIGHT - 4 * offset) / 2,
                WIDTH - offset * 2 + offset + 10, offset * 2 + (HEIGHT - 4 * offset) / 2);
        g.drawLine(WIDTH - offset * 2 + offset + 10, offset * 2 + (HEIGHT - 4 * offset) / 2,
                WIDTH - offset * 2 + offset + 10, offset * 3 / 2);
        g.drawLine(WIDTH - offset * 2 + offset + 10, offset * 3 / 2,
                offset / 2, offset * 3 / 2);
        g.drawLine(offset / 2, offset * 3 / 2,
                offset / 2, HEIGHT - offset * 3 / 2);

        // draw c
        for(int i = 1; i < 11; i++) {
            int j = 10 - i;
            if(((lfsr.getC() >> j) & 1) == 1 &&
                    (j != lfsr.getN())) {
                int xOffset = 10 * (5 * i + 2);
                g.drawLine(xOffset, (int) (HEIGHT - offset * 1.5), xOffset, (int) ((i + 1) * 30.5));
                g.drawLine(xOffset, (int) ((i + 1) * 30.5), WIDTH - offset * 2, (int) ((i + 1) * 30.5));
            }
        }

        // draw state (from seed)
        String seedBin = String.format("%" + lfsr.getN() + "s", Integer.toBinaryString(curr)).replace(' ', '0');
        int index = seedBin.length() - 1;
        for(int i = 495; i > 0; i -= 50) {
            if(index < 0) break;
            g.drawString(String.valueOf(seedBin.charAt(index--)), i, HEIGHT - offset * 3 / 2);
        }
    }

}
