package front;

import logic.LFSR;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code JFrame} class to show the output
 * of Linear feedback shift register
 */
public class LFSRShowFrame extends FrameBase {

    /**
     * Default LFSR to use
     */
    private final LFSR lfsr;

    /**
     * Default seed to start from
     */
    private final int seed;

    /**
     * All possible binary string
     */
    private List<String> strings;

    /**
     * Bin string of states
     */
    private StringBuilder _bin;

    /**
     * {@code JLabel} to show binary string
     */
    private JLabel lBin;

    private JLabel lTth, lTcalc;

    /**
     * {@code JList} of values
     */
    private JList<String> list;

    @Override
    protected void setupUI() {
        lBin = new JLabel("BIN : ");
        list = new JList<>();

        JPanel pT = new JPanel(new GridLayout(1, 2));
        lTth = new JLabel("Tth : ");
        lTcalc = new JLabel("Tcalc : ");

        JScrollPane scrollPane = new JScrollPane(list);

        add(lBin);
        pT.add(lTth);
        pT.add(lTcalc);
        add(pT);
        add(scrollPane);
    }

    @Override
    protected void setupButtons() {

    }

    @Override
    protected void setupMenu() {
        JMenuBar menuBar = JMenuBarFactory.createJMenuBar(new String[] {"File"}, new String[][]{{"Save", "Close"}},
                new ActionListener[][]{{e -> {
                    // need to save curr bin string
                    // Tth & Tcalc
                    // all values
                    // to file
                    JFileChooser saveFile = new JFileChooser(); // create JFileChooser
                    saveFile.addChoosableFileFilter(new FileNameExtensionFilter("Only .txt files", ".txt"));
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

                    // if file isn't null
                    if(fileToWriteTo != null) {
                        try (OutputStream outputStream = new FileOutputStream(fileToWriteTo); // open outputStream
                             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream)) { // creat outputStreamWriter
                            StringBuilder outputBuilder = new StringBuilder(); // all output build with StringBuilder
                            outputBuilder.append("BIN : ").append(_bin).append("\n");
                            outputBuilder.append(lTth.getText()).append("\n");
                            outputBuilder.append(lTcalc.getText()).append("\n");
                            strings.forEach(str -> outputBuilder.append(str).append("\n"));
                            outputStreamWriter.write(outputBuilder.toString()); // writer
                        } catch (IOException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                    }
                }, e -> dispose()}});
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

    private void getInnerParams() {
        strings = new ArrayList<>(); // we have no binary string in the beginning
        _bin = new StringBuilder(); // same to bin string
        int tmpSeed = seed; // first element is seed
        for(int i = 0; i < Math.pow(2, lfsr.getN()); i++) { // iterate to 2^n - 1
            _bin.append(tmpSeed & 1); // next element of bin string is y[0]
            // add new state of register
            strings.add(i + ") " + String.format("%" + lfsr.getN() + "s",
                    Integer.toBinaryString(tmpSeed)).replace(' ', '0'));
            tmpSeed = lfsr.next(tmpSeed); // generate new value
        }
    }

    private void setInnerParams() {
        lBin.setText("BIN : " + _bin);
        list.setListData(strings.toArray(new String[0]));
        lTth.setText("Tth : " + lfsr.getT());
        lTcalc.setText("Tcalc : " + lfsr.getTActual());
    }

    /**
     * Constructor
     *
     * @param lfsr linear forward shift register to operate with
     * @param seed the initial seed of the logic.LFSR
     */
    public LFSRShowFrame(LFSR lfsr, int seed) {
        super(lfsr.getN() + " " + lfsr.getC(), 600, 600, new GridLayout(3, 1));

        this.lfsr = lfsr;
        this.seed = seed;

        getInnerParams();
        setInnerParams();
    }

    @Override
    public void paint(Graphics g) {
        // Try to build something like graph of activations
        // builds from binary string

        super.paint(g);

        // additional Y to skip menu
        g.translate(0, menuSkip);

        // set color and print region
        g.setColor(Color.cyan); // background color
        g.fillRect(0, 0, 600, 60);
        String bin = _bin.toString(); // need to have string

        g.setColor(Color.black);
        // set default offsets
        int xOffset = 10;
        int yOffset = 10;

        // if this value changes we should draw v line
        boolean prev = false;
        int step = 20; // default step
        for(int i = 0; i < bin.length(); i++) {
            int x = xOffset + (i * step);
            int y = yOffset + 40;
            if(i != 0) {
                if((prev && bin.charAt(i) == '0') || (!prev && bin.charAt(i) == '1')) {
                    // draw v line
                    g.drawLine(x, y - step, x, y);
                }
            }
            if(bin.charAt(i) == '0') {
                g.drawLine(x, y, x + step, y);
                prev = false;
            }
            else {
                g.drawLine(x, y - step, x + step, y - step);
                prev = true;
            }
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
