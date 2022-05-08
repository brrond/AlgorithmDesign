package front;

import logic.LFSR;

import javax.swing.*;
import java.awt.*;

/**
 * Simple {@code JFrame} class to show the possibilities
 * of Linear feedback shift register
 */
public class LFSRFrame extends JFrame {

    /**
     * UI Elements
     */
    private JTextField tfN, tfC, tfSeed;

    /**
     * Inner state
     */
    private int n, C, seed;

    /**
     * The font to used in this project
     * All the windows use this font
     */
    public static final Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    /**
     * Prints error message msg.
     *
     * @param msg massage to print
     */
    public static void handleException(String msg) {
        JFrame frame = new JFrame(); // create frame
        frame.setSize(500, 200); // set its size
        frame.setLayout(new FlowLayout()); // set flow layout because we only two elements
        JLabel label = new JLabel(msg); // create msg
        frame.add(label);
        JButton ok = new JButton("OK"); // create OK button
        ok.addActionListener(event -> frame.dispose()); // set button event
        frame.add(ok); // add button
        frame.setVisible(true); // show frame
    }

    /**
     * Adds initial values to text fields
     */
    private void tmpInit() {
        tfN.setText("4");
        tfC.setText("10011");
        tfSeed.setText("1010");
    }

    /**
     * Create user interface elements
     */
    private void setupUI() {
        // init labels
        JLabel lN = new JLabel("N : ");
        JLabel lCoefficient = new JLabel("Coefficient : ");
        JLabel lSeed = new JLabel("Seed : ");
        lN.setFont(font);
        lCoefficient.setFont(font);
        lSeed.setFont(font);

        // init text fields
        tfN = new JTextField();
        tfC = new JTextField();
        tfSeed = new JTextField();
        tfN.setFont(font);
        tfC.setFont(font);
        tfSeed.setFont(font);

        // add all created elements
        add(lN);
        add(tfN);
        add(lCoefficient);
        add(tfC);
        add(lSeed);
        add(tfSeed);
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

    /**
     * Creates buttons (contains business logic)
     */
    private void setupButtons() {
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
        bShow.setFont(font);
        bBuild.setFont(font);
        bGraph.setFont(font);
        bExit.setFont(font);

        // add all components to window
        add(bShow);
        add(bBuild);
        add(bGraph);
        add(bExit);
    }

    /**
     * Creates user menus
     */
    private void setupMenu() {
        // create menu itself
        JMenuBar menuBar = new JMenuBar();

        // create menu elements
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");

        // fill "File" menu
        JMenuItem exit = new JMenuItem("Exit");

        // fill "Irreducible" menu
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem set = new JMenuItem("Set");
        JMenuItem custom = new JMenuItem("Custom");

        // add actions
        clear.addActionListener(e -> {
            tfN.setText("");
            tfC.setText("");
            tfSeed.setText("");
        });
        exit.addActionListener(e -> dispose());
        set.addActionListener(e -> new IrreduciblePolynomialFrame(this));
        custom.addActionListener(e -> new CustomPolynomialsFrame());

        // set "File" menu
        menuBar.add(menuFile);
        menuFile.add(exit);

        // set "Edit" menu
        menuBar.add(menuEdit);
        menuEdit.add(clear);
        menuEdit.add(set);
        menuEdit.add(custom);

        // set menuBar to window
        this.setJMenuBar(menuBar);
    }

    /**
     * Constructor. It initialises std params
     */
    public LFSRFrame() {
        super();

        setLayout(new GridLayout(5, 2)); // uses GridLayout
        setTitle("Linear feedback shift register"); // set title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // this is main window

        // set window params
        setFont(font);
        setResizable(false);
        setSize(400, 400);

        // init UI elements
        setupUI();
        setupButtons();
        setupMenu();

        // default init
        tmpInit();

        // show window
        setVisible(true);
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
