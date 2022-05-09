package front;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

/**
 * Frame to create base of custom polynomials
 */
public class CustomPolynomialsFrame extends JFrame {

    /**
     * Path to custom polynomials
     */
    public final String path = "./resource/custom_polynomials.ser";

    /**
     * Column names
     */
    private final String[] columnNames = new String[]{"N", "Num"};

    /**
     * Actual model with data
     */
    private DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    /**
     * <code>JTable</code> to show polynomials
     */
    private JTable table;

    /**
     * Saves model to file (serialize)
     */
    private void saveModel() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load model from file (deserialize)
     */
    private void loadModel() {
        File file = new File(path);
        if(file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(path);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
                model = (DefaultTableModel) inputStream.readObject();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                if(file.delete()) loadModel();
                return;
            }
            table.setModel(model);
        }
    }

    private void setupUI() {
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setFont(LFSRFrame.font);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setFont(LFSRFrame.font);

        JLabel lN = new JLabel("N : ");
        JLabel lNum = new JLabel("Num : ");
        JTextField tfN = new JTextField();
        JTextField tfNum = new JTextField();
        JButton bDelete = new JButton("Delete");
        JButton bAdd = new JButton("Add");
        JButton bClear = new JButton("Clear");
        JButton bClose = new JButton("Close");

        lN.setFont(LFSRFrame.font);
        lNum.setFont(LFSRFrame.font);
        tfN.setFont(LFSRFrame.font);
        tfNum.setFont(LFSRFrame.font);
        bDelete.setFont(LFSRFrame.font);
        bAdd.setFont(LFSRFrame.font);
        bClear.setFont(LFSRFrame.font);
        bClose.setFont(LFSRFrame.font);

        bClose.addActionListener(e -> dispose());
        bClear.addActionListener(e -> {
            tfN.setText("");
            tfNum.setText("");
        });
        bDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            model.removeRow(selected);
        });
        bAdd.addActionListener(e -> {
            String sN = tfN.getText();
            String sNum = tfNum.getText();

            int n, num;
            try {
                n = Integer.parseInt(sN);
            } catch(NumberFormatException exception) {
                LFSRFrame.handleException("N should be number");
                return;
            }

            if(n <= 0 || n > 32) {
                LFSRFrame.handleException("N can't be less then zero or grater then 32");
                return;
            }

            try {
                num = Integer.parseInt(sNum.substring(0, sNum.length() - 1), 8);
            } catch(NumberFormatException exception) {
                LFSRFrame.handleException("Num should be a polynomial from table in form \"###{A-H}\"");
                return;
            }

            if((num & (1 << n)) == 0 || (num & 1) == 0) {
                LFSRFrame.handleException("Num_2[0] * Num_2[N] != 1");
                return;
            }

            model.addRow(new String[]{sN, sNum});
        });

        panel.add(lN);
        panel.add(tfN);
        panel.add(lNum);
        panel.add(tfNum);
        panel.add(bDelete);
        panel.add(bAdd);
        panel.add(bClear);
        panel.add(bClose);

        add(table);
        add(panel);
    }

    public CustomPolynomialsFrame() {
        setTitle("Custom table");
        setSize(400, 400);
        setLayout(new GridLayout(2, 1));

        setupUI();
        loadModel(); // on start of the frame should load data

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        saveModel(); // when JFrame disposed
                     // saves data to file
    }

}
