package front;

import logic.LFSR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Simple {@code JFrame} class to show the possibilities
 * of Linear feedback shift register
 */
public class LFSRFrame extends FrameBase {

    /**
     * UI Elements
     */
    private JTextField tfN, tfC, tfSeed;

    /**
     * Inner state
     */
    private int n, C, seed;

    /**
     * Adds initial values to text fields
     */
    private void tmpInit() {
        tfN.setText("4");
        tfC.setText("10011");
        tfSeed.setText("1010");
    }

    /**
     * Method to get values from user and parse them
     */
    private void parseValues() {
        // gets all inputs and tries to convert to int
        String sn = tfN.getText();
        String sC = tfC.getText();
        String sseed = tfSeed.getText();
        try {
            n = Integer.parseInt(sn);
            C = Integer.parseInt(sC, 2);
            seed = Integer.parseInt(sseed, 2);
            if(n == 0 || C == 0 || seed == 0) throw new NumberFormatException();
        } catch(NumberFormatException exception) {
            handleException("n, Coefficient and seed must be numbers. Coefficient and seed must be binary");
        } catch(IllegalArgumentException illegalArgumentException) {
            handleException("Impossible values");
        } catch(Exception e) {
            handleException("Something went wrong");
        }
    }

    @Override
    protected void setupUI() {
        // init labels
        JLabel lN = new JLabel("N : ");
        JLabel lCoefficient = new JLabel("Coefficient : ");
        JLabel lSeed = new JLabel("Seed : ");

        // init text fields
        tfN = new JTextField();
        tfC = new JTextField();
        tfSeed = new JTextField();

        // add all created elements
        add(lN);
        add(tfN);
        add(lCoefficient);
        add(tfC);
        add(lSeed);
        add(tfSeed);
    }

    @Override
    protected void setupButtons() {
        // init show button
        JButton bShow = new JButton("Show");
        bShow.addActionListener(event -> {
            parseValues();
            try {
                new LFSRShowFrame(new LFSR(n, C), seed);
            } catch(IllegalArgumentException e) {
                handleException(e.getMessage());
            }
        });

        // init build button
        JButton bBuild = new JButton("Build");
        bBuild.addActionListener(event -> {
            parseValues();
            if(n > 10) {
                handleException("To use builder you should use no more then 10 triggers.");
                return;
            }
            new LFSRBuilderFrame(new LFSR(n, C), seed);
        });

        // init clear button
        JButton bGraph = new JButton("Clear");
        bGraph.addActionListener(event -> {
            tfN.setText("");
            tfC.setText("");
            tfSeed.setText("");
        });

        // init exit button
        JButton bExit = new JButton("Exit");
        bExit.addActionListener(action -> System.exit(0));

        // add all components to window
        add(bShow);
        add(bBuild);
        add(bGraph);
        add(bExit);
    }

    @Override
    protected void setupMenu() {
        setJMenuBar(JMenuBarFactory.createJMenuBar(new String[]{"File", "Edit"},
                new String[][]{{"Exit"}, {"Clear", "Set"}},
                new ActionListener[][]{new ActionListener[] { e -> dispose() }, new ActionListener[] {
                        e -> {
                            tfN.setText("");
                            tfC.setText("");
                            tfSeed.setText("");
                        }, e -> new IrreduciblePolynomialFrame(this)
                }}));
    }

    /**
     * Constructor. It initialises std params
     */
    public LFSRFrame() {
        super("Linear feedback shift register", 400, 400, new GridLayout(5, 2));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this is main window

        tmpInit();
    }

    /**
     * Sets count of triggers
     *
     * @param N integer count of triggers
     */
    public void setN(int N) {
        tfN.setText(String.valueOf(N));
    }

    /**
     * Sets coefficients
     *
     * @param C integer value of C
     */
    public void setCoefficient(int C) {
        setCoefficient(Integer.toBinaryString(C));
    }

    /**
     * Sets coefficients
     *
     * @param C binary string of coefficients
     */
    public void setCoefficient(String C) {
        tfC.setText(C);
    }

}
