package front;

import logic.LFSR;

import javax.swing.*;
import java.awt.*;

public class LFSRFrame extends JFrame {

    // UI elements
    private JLabel lN, lCoefficient, lSeed;
    private JTextField tfN, tfC, tfSeed;
    private JButton bShow, bBuild, bGraph, bExit;


    // standard font for all elements in this project
    public static final Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    /**
     * Prints error message msg.
     *
     * @param msg massage to print
     */
    private void handleException(String msg) {
        JFrame frame = new JFrame();
        frame.setSize(500, 200);
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel(msg);
        frame.add(label);
        JButton ok = new JButton("OK");
        ok.addActionListener(event -> frame.dispose());
        frame.add(ok);
        frame.setVisible(true);
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
        lN = new JLabel("N : ");
        lCoefficient = new JLabel("Coefficient : ");
        lSeed = new JLabel("Seed : ");
        lN.setFont(font);
        lCoefficient.setFont(font);
        lSeed.setFont(font);

        tfN = new JTextField();
        tfC = new JTextField();
        tfSeed = new JTextField();
        tfN.setFont(font);
        tfC.setFont(font);
        tfSeed.setFont(font);

        add(lN);
        add(tfN);
        add(lCoefficient);
        add(tfC);
        add(lSeed);
        add(tfSeed);
    }

    /**
     * Creates buttons (contains business logic)
     */
    private void setupButtons() {
        bShow = new JButton("Show");
        bShow.addActionListener(event -> {
            // gets all inputs and tries to convert to int
            String sn = tfN.getText();
            String sC = tfC.getText();
            String sseed = tfSeed.getText();
            try {
                int n = Integer.parseInt(sn);
                int C = Integer.parseInt(sC, 2);
                int seed = Integer.parseInt(sseed, 2);
                if(n == 0 || C == 0 || seed == 0) throw new NumberFormatException();
                new LFSRShowFrame(new LFSR(n, C), seed);
            } catch(NumberFormatException exception) {
                handleException("n, Coefficient and seed must be numbers. Coefficient and seed must be binary");
            } catch(IllegalArgumentException illegalArgumentException) {
                handleException("Impossible values");
            } catch(Exception e) {
                handleException("Something went wrong");
            }
        });

        bBuild = new JButton("Build");
        bBuild.addActionListener(event -> {
            // TODO This is duplicate
            // gets all inputs and tries to convert to int
            String sn = tfN.getText();
            String sC = tfC.getText();
            String sseed = tfSeed.getText();
            try {
                int n = Integer.parseInt(sn);
                int C = Integer.parseInt(sC, 2);
                int seed = Integer.parseInt(sseed, 2);
                if(n == 0 || C == 0 || seed == 0) throw new NumberFormatException();
                new LFSRBuilderFrame(new LFSR(n, C), seed);
            } catch(NumberFormatException exception) {
                handleException("n, Coefficient and seed must be numbers. Coefficient and seed must be binary");
            } catch(IllegalArgumentException illegalArgumentException) {
                handleException("Impossible values");
            } catch(Exception e) {
                handleException("Something went wrong");
            }
        });

        bGraph = new JButton("Clear");
        bGraph.addActionListener(event -> {
            tfN.setText("");
            tfC.setText("");
            tfSeed.setText("");
        });

        bExit = new JButton("Exit");
        bExit.addActionListener(action -> System.exit(0));
        bShow.setFont(font);
        bBuild.setFont(font);
        bGraph.setFont(font);
        bExit.setFont(font);

        add(bShow);
        add(bBuild);
        add(bGraph);
        add(bExit);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem exit = new JMenuItem("Exit");

        JMenu menuIrreducible = new JMenu("Irreducible");
        JMenuItem irreducible = new JMenuItem("Irreducible polynomial");

        clear.addActionListener(e -> {
            tfN.setText("");
            tfC.setText("");
            tfSeed.setText("");
        });

        exit.addActionListener(e -> bExit.doClick());

        irreducible.addActionListener(e -> new IrreduciblePolynomialFrame(this));

        menuBar.add(menuFile);
        menuFile.add(clear);
        menuFile.add(exit);
        menuBar.add(menuIrreducible);
        menuIrreducible.add(irreducible);
        this.setJMenuBar(menuBar);
    }

    /**
     * Constructor. It initialises std params
     */
    public LFSRFrame() {
        super();

        setLayout(new GridLayout(5, 2));
        setTitle("logic.LFSR example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFont(font);
        setResizable(false);
        setSize(400, 400);

        setupUI();
        setupButtons();
        setupMenu();

        tmpInit(); // TODO Think about it

        setVisible(true);
    }

    public void setN(int N) {
        tfN.setText(String.valueOf(N));
    }

    public void setCoefficient(int C) {
        setCoefficient(Integer.toBinaryString(C));
    }

    public void setCoefficient(String C) {
        tfC.setText(C);
    }
}
