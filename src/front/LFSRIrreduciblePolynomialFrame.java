package front;

import logic.IrreduciblePolynomials;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * JFrame to choose polynomial coefficients
 */
public class LFSRIrreduciblePolynomialFrame extends FrameBase {

    /**
     * {@code JList<String>} of all poolynomials
     */
    private JList<String> listPolynomials;

    /**
     * Reference to main window ({@code LFSRFrame}) t to change values
     */
    private final LFSRFrame mainFrame;

    /**
     * {@code JLabel} to show selected polynomial
     */
    private JLabel lResult;

    /**
     * {@code JLabel} to show the actual polynomial
     */
    private JLabel lPolynomial;

    @Override
    public void setupUI() {
        // create list
        listPolynomials = new JList<>(IrreduciblePolynomials.getListData());

        // make it scrollable
        JScrollPane listScroll = new JScrollPane();
        listScroll.setViewportView(listPolynomials);
        listPolynomials.setLayoutOrientation(JList.VERTICAL);

        // init labels
        lResult = new JLabel("");
        lPolynomial = new JLabel("");

        // add action to list
        listPolynomials.addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) {
                return;
            }
            String el = listPolynomials.getSelectedValue();

            lResult.setText(el + " : ");
            String power = el.split(" ")[0];
            power = power.substring(1);
            String value = el.split(" ")[1];
            value = value.substring(0, value.length() - 1);
            String C = Integer.toBinaryString(Integer.valueOf(value, 8));
            int N = Integer.parseInt(power);

            mainFrame.setN(N);
            mainFrame.setCoefficient(C);

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < C.length(); i++) {
                int step = N - i;
                if(C.charAt(i) == '1') {
                    if(step != 0) {
                        res.append("x^").append(step).append(" + ");
                    } else {
                        res.append("1 + ");
                    }
                }
            }
            lPolynomial.setText(res.substring(0, res.length() - 2));
        });

        add(listScroll);
        add(lResult);
        add(lPolynomial);
    }

    @Override
    protected void setupButtons() {
        JButton bClose = new JButton("Close");
        bClose.addActionListener(e -> this.dispose());
        add(bClose);
    }

    @Override
    protected void setupMenu() {

    }

    /**
     * Constructor
     *
     * @param mainFrame to change values in
     */
    public LFSRIrreduciblePolynomialFrame(LFSRFrame mainFrame) {
        super("Irreducible Polynomial", 400, 600, new GridLayout(4, 1));

        this.mainFrame = mainFrame;
    }
}
