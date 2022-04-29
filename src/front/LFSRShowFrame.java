package front;

import logic.LFSR;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LFSRShowFrame extends JFrame {

    // constructor params
    private LFSR lfsr;
    private int seed;

    // inner state params
    private List<String> strings;
    StringBuilder _bin;

    // UI vars
    private JLabel lBin;
    private JList<String> list;
    private JScrollPane scrollPane;

    /**
     * Setups user interface elements
     */
    private void setupUI() {
        lBin = new JLabel("BIN : ");
        list = new JList();
        scrollPane = new JScrollPane(list);
        lBin.setFont(LFSRFrame.font);
        list.setFont(LFSRFrame.font);
        scrollPane.setFont(LFSRFrame.font);

        add(lBin);
        add(scrollPane);
    }

    /**
     * Gets business logic params
     */
    private void getInnerParams() {
        strings = new ArrayList<>();
        _bin = new StringBuilder();
        int tmpSeed = seed;
        for(int i = 0; i < Math.pow(2, lfsr.getN()) - 1; i++) {
            _bin.append(tmpSeed & 1);
            strings.add(i + ") " + String.format("%" + lfsr.getN() + "s",
                    Integer.toBinaryString(tmpSeed)).replace(' ', '0'));
            tmpSeed = lfsr.next(tmpSeed);
        }
    }

    /**
     * Sets business logic params
     */
    private void setInnerParams() {
        lBin.setText("BIN : " + _bin);
        list.setListData(strings.toArray(new String[strings.size()]));
    }

    /**
     * Constructor
     *
     * @param lfsr linear forward shift register to operate with
     * @param seed the initial seed of the logic.LFSR
     */
    public LFSRShowFrame(LFSR lfsr, int seed) { // TODO Create ShowState to divide business logic from front
        this.lfsr = lfsr;
        this.seed = seed;

        setTitle(lfsr.getN() + " " + lfsr.getC());
        setLayout(new GridLayout(2, 1));
        setSize(600, 500);
        setResizable(false);

        setupUI();
        getInnerParams();
        setInnerParams();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // set color and print region
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 600, 100);
        String bin = _bin.toString();

        g.setColor(Color.black);
        // set default offsets
        int xOffset = 10;
        int yOffset = 10;

        // if this value changes we should draw v line
        boolean prev = false;
        int step = 20; // default step
        for(int i = 0; i < bin.length(); i++) {
            int x = xOffset + (i * step);
            int y = yOffset + 80;
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
}
