package front;

import logic.LFSR;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Linear feedback shift register builder frame.
 * Draws scheme of linear feedback shift register
 */
public class LFSRBuilderFrame extends JFrame {

    /**
     * Linear feedback shift register itself
     */
    private final LFSR lfsr;

    /**
     * Current state
     */
    private int curr;

    /**
     * UserInterface elements
     */
    JLabel lCurr10, lCurr2;
    JTextField tfCurr10, tfCurr2;
    JButton bNext, bClose;

    /**
     * Setups user interface
     */
    private void setupUI() {
        setFont(LFSRFrame.font);

        // create labels
        lCurr10 = new JLabel("Curr (10) : ");
        lCurr2 = new JLabel("Curr (2) : ");

        // create fields
        tfCurr10 = new JTextField();
        tfCurr2 = new JTextField();

        // set fonts
        lCurr10.setFont(LFSRFrame.font);
        lCurr2.setFont(LFSRFrame.font);
        tfCurr10.setFont(LFSRFrame.font);
        tfCurr2.setFont(LFSRFrame.font);

        // debug elements
        @SuppressWarnings("MismatchedReadAndWriteOfArray") JLabel[] debugs = new JLabel[12];
        for(JLabel label : debugs) {
            label = new JLabel("");
            label.setVisible(false);
            add(label);
        }

        // add elements to window
        add(lCurr10);
        add(tfCurr10);
        add(lCurr2);
        add(tfCurr2);
    }

    /**
     * Setups menu elements
     */
    private void setupMenu(){
        // create menuBar
        JMenuBar menuBar = new JMenuBar();

        // create fileMenu
        JMenu fileMenu = new JMenu("File");

        // create fileMenu items
        JMenuItem save = new JMenuItem("Save");
        JMenuItem close = new JMenuItem("Close");

        // add events
        save.addActionListener(e -> {
            // this is event to save current scheme to .png file
            JFileChooser saveFile = new JFileChooser(); // create JFileChooser
            saveFile.addChoosableFileFilter(new FileNameExtensionFilter("Only .png files", ".png"));
            int saveDialogResult = saveFile.showSaveDialog(null); // show dialog
            File fileToWriteTo = null;
            switch (saveDialogResult) {
                case JFileChooser.APPROVE_OPTION: // if user have chosen some file
                    fileToWriteTo = saveFile.getSelectedFile(); // we get this file
                    break;
                case JFileChooser.ERROR_OPTION:
                case JFileChooser.CANCEL_OPTION:
                default:
                    break;
            }

            if(fileToWriteTo != null) {
                // create image 600x400
                BufferedImage bufferedImage = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
                Graphics graphicsToDraw = bufferedImage.createGraphics();
                graphicsToDraw.translate(0, -50); // translate graphics position so it can skip title and menu
                paintAll(graphicsToDraw); // paint with help of local paint function
                try {
                    ImageIO.write(bufferedImage, "png", fileToWriteTo); // copy&paster from stackoverflow
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
        close.addActionListener(e -> dispose());

        // add elements to menus
        fileMenu.add(save);
        fileMenu.add(close);

        // add menus to menuBar
        menuBar.add(fileMenu);

        // add menuBar to form
        setJMenuBar(menuBar);

        MouseListener myMouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
                repaint();
            }
        };
        menuBar.addMouseListener(myMouseListener);
    }

    /**
     * Setups buttons and buttons listeners
     */
    private void setupButtons() {
        // create buttons
        bNext = new JButton("Next");
        bClose = new JButton("Close");

        // add action to buttons
        bNext.addActionListener(event -> {
            curr = lfsr.next(curr); // generate next state
            repaint(); // repaint window

            // set state
            tfCurr10.setText(String.valueOf(curr));
            tfCurr2.setText(String.format("%" + lfsr.getN() + "s", Integer.toBinaryString(curr)).replace(' ', '0'));
        });
        bClose.addActionListener(event -> dispose());

        // add buttons to form
        add(bNext);
        add(bClose);
    }

    /**
     * Constructor
     *
     * @param lfsr linear forward shift register to work with
     * @param seed default init
     */
    public LFSRBuilderFrame(LFSR lfsr, int seed) {
        // TODO Create BuilderState to divide business logic from front
        super();

        this.lfsr = lfsr;
        this.curr = seed;

        setTitle("LFSR Builder");
        setResizable(false);
        setLayout(new GridLayout(9, 2));
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
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
        setupMenu();

        tfCurr10.setText(String.valueOf(curr));
        tfCurr2.setText(String.format("%" + lfsr.getN() + "s", Integer.toBinaryString(curr)).replace(' ', '0'));
        setupButtons();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(0, 50); // adds menuBar skip

        int WIDTH = 600;
        int HEIGHT = 400;
        int offset = 30;

        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // draw output of sum
        g.fillRect(offset / 2, HEIGHT / 2, offset, offset);

        g.setColor(Color.black);
        g.drawString(String.valueOf(lfsr.outputOfSum(curr)), offset / 3 * 2, HEIGHT / 2); // show output of sum
        g.drawLine(offset / 2, (int) (HEIGHT - 1.5 * offset), WIDTH - 10, (int) (HEIGHT - 1.5 * offset));
        g.drawString("out", WIDTH - offset * 3 / 2, HEIGHT - offset * 5 / 6);

        // draw triggers
        for(int i = 0; i < lfsr.getN(); i++) {
            int j = 10 - i - 1;
            g.setColor(Color.cyan);
            g.fillRect(offset + (50 * j), HEIGHT - offset * 2, 30, 30);
            g.setColor(Color.black);
            g.drawRect(offset + (50 * j), HEIGHT - offset * 2, 30, 30);
        }

        // draw sum and its lines
        g.drawRect(WIDTH - offset * 2, offset * 2, offset, HEIGHT - 4 * offset);
        g.drawLine(WIDTH - offset * 2 + offset, offset * 2 + (HEIGHT - 4 * offset) / 2,
                WIDTH - offset * 2 + offset + 10, offset * 2 + (HEIGHT - 4 * offset) / 2);
        g.drawLine(WIDTH - offset * 2 + offset + 10, offset * 2 + (HEIGHT - 4 * offset) / 2,
                WIDTH - offset * 2 + offset + 10, offset * 3 / 2);
        g.drawLine(WIDTH - offset * 2 + offset + 10, offset * 3 / 2,
                offset / 2, offset * 3 / 2);
        g.drawLine(offset / 2, offset * 3 / 2,
                offset / 2, HEIGHT - offset * 3 / 2);

        // draw c (lines)
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
