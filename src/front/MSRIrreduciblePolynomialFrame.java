package front;

import logic.IrreduciblePolynomials;

import javax.swing.*;
import java.awt.*;

public class MSRIrreduciblePolynomialFrame extends FrameBase {

    private final MSRFrame mainFrame;
    private JComboBox<String> cbPolynomials1;
    private JComboBox<String> cbPolynomials2;
    private JLabel lPolynomialA;
    private JLabel lPolynomialB;

    public MSRIrreduciblePolynomialFrame(MSRFrame mairFrame) {
        super("Irreducible Polynomial", 400, 600, new GridLayout(5, 1));
        this.mainFrame = mairFrame;
    }

    @Override
    public void setupUI() {
        // create list
        cbPolynomials1 = new JComboBox<>(IrreduciblePolynomials.getListData());
        cbPolynomials2 = new JComboBox<>(IrreduciblePolynomials.getListData());

        // init labels
        lPolynomialA = new JLabel("");
        lPolynomialB = new JLabel("");
        JLabel lSelectA = new JLabel("First : ");
        JLabel lSelectB = new JLabel("Second : ");

        // add action to list
        cbPolynomials1.addActionListener(e -> {
            String el = cbPolynomials1.getItemAt(cbPolynomials1.getSelectedIndex());

            // TODO Dilate duplicate
            String power = el.split(" ")[0];
            power = power.substring(1);
            String value = el.split(" ")[1];
            value = value.substring(0, value.length() - 1);
            String C = Integer.toBinaryString(Integer.valueOf(value, 8));
            int N = Integer.parseInt(power);

            mainFrame.setN(N);
            mainFrame.setCa(C);

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
            lPolynomialA.setText(res.substring(0, res.length() - 2));
        });
        cbPolynomials2.addActionListener(e -> {
            String el = cbPolynomials2.getItemAt(cbPolynomials2.getSelectedIndex());

            // TODO Dilate duplicate
            String power = el.split(" ")[0];
            power = power.substring(1);
            String value = el.split(" ")[1];
            value = value.substring(0, value.length() - 1);
            String C = Integer.toBinaryString(Integer.valueOf(value, 8));
            int M = Integer.parseInt(power);

            mainFrame.setM(M);
            mainFrame.setCb(C);

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < C.length(); i++) {
                int step = M - i;
                if(C.charAt(i) == '1') {
                    if(step != 0) {
                        res.append("x^").append(step).append(" + ");
                    } else {
                        res.append("1 + ");
                    }
                }
            }
            lPolynomialB.setText(res.substring(0, res.length() - 2));
        });

        // create panels
        JPanel panelFirstInput = new JPanel(new GridLayout(1, 2));
        JPanel panelFirstPolynomial = new JPanel(new GridLayout(1, 1));
        JPanel panelSecondInput = new JPanel(new GridLayout(1, 2));
        JPanel panelSecondPolynomial = new JPanel(new GridLayout(1, 1));

        // add all this mess
        panelFirstInput.add(lSelectA);
        panelFirstInput.add(cbPolynomials1);
        add(panelFirstInput);

        panelFirstPolynomial.add(lPolynomialA);
        add(panelFirstPolynomial);

        panelSecondInput.add(lSelectB);
        panelSecondInput.add(cbPolynomials2);
        add(panelSecondInput);

        panelSecondPolynomial.add(lPolynomialB);
        add(panelSecondPolynomial);
    }

    @Override
    protected void setupButtons() {
        JButton bClose = new JButton("Close");
        bClose.addActionListener(e -> this.dispose());
        JPanel panelButton = new JPanel(new GridLayout(1, 1));
        panelButton.add(bClose);
        add(panelButton);
    }

    @Override
    protected void setupMenu() {
    }
}

