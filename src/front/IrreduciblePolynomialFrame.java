package front;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame to choose polynomial coefficients
 */
public class IrreduciblePolynomialFrame extends JFrame {

    private JList<String> listPolynomials;
    private JScrollPane listScroll;
    private LFSRFrame mainFrame;

    private JLabel lResult;
    private JLabel lPolynomial;

    private JButton bClose;

    /**
     * Method to get all irreducible polynomials
     *
     * @return {@code String[]} of irreducible polynomials
     */
    private String[] getListData() {
        return new String[]{"^2 7H",
                "^3 13F",
                "^4 23F","^4 37D",
                "^5 45E","^5 75G","^5 67H",
                "^6 103F","^6 127B","^6 147H","^6 111A","^6 155E",
                "^7 211E","^7 217E","^7 235E","^7 367H","^7 277E",
                "^8 435E","^8 567B","^8 763D","^8 551E","^8 675C",
                "^9 1021E","^9 1131E","^9 1451G","^9 1231A","^9 1423G",
                "^10 2011E","^10 2017B","^10 24215E","^10 3771G","^10 2357B",
                "^11 4005E","^11 4445E","^11 4215E","^11 4055E","^11 6015G",
                "^12 10123F","^12 12133B","^12 10115A","^12 12153B","^12 11765A",
                "^13 20033F","^13 23261E","^13 24623F","^13 23517F","^13 30741G",
                "^14 47103","^14 40547B","^14 43333E","^14 51761E","^14 54055A"};

        // TODO Add read from file
    }

    public void setupUI() {
        listPolynomials = new JList<>(getListData());
        listPolynomials.setFont(LFSRFrame.font);
        listScroll = new JScrollPane();
        listScroll.setViewportView(listPolynomials);
        listPolynomials.setLayoutOrientation(JList.VERTICAL);

        lResult = new JLabel("");
        lResult.setFont(LFSRFrame.font);

        lPolynomial = new JLabel("");
        lPolynomial.setFont(LFSRFrame.font);

        bClose = new JButton("Close");

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

        bClose.addActionListener(e -> this.dispose());

        add(listScroll);
        add(lResult);
        add(lPolynomial);
        add(bClose);
    }

    public IrreduciblePolynomialFrame(LFSRFrame mainFrame) {
        super();

        this.mainFrame = mainFrame;

        setLayout(new GridLayout(4, 1));
        setTitle("Irreducible Polynomial");

        setupUI();

        setFont(LFSRFrame.font);
        setResizable(false);
        setSize(400, 600);

        setVisible(true);
    }
}
