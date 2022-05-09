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
public class CustomPolynomialsFrame extends FrameBase {

    /**
     * Path to custom polynomials
     */
    public static final String path = "./resource/custom_polynomials.ser";

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
     * JPanel that contains all other elements except JTable
     */
    private JPanel panel;

    private JTextField tfN, tfNum;

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

    @Override
    protected void setupUI() {
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);

        panel = new JPanel(new GridLayout(4, 2));

        JLabel lN = new JLabel("N : ");
        JLabel lNum = new JLabel("Num : ");
        tfN = new JTextField();
        tfNum = new JTextField();

        panel.add(lN);
        panel.add(tfN);
        panel.add(lNum);
        panel.add(tfNum);

        add(table);
        add(panel);
    }

    @Override
    protected void setupButtons() {
        JButton bDelete = new JButton("Delete");
        JButton bAdd = new JButton("Add");
        JButton bClear = new JButton("Clear");
        JButton bClose = new JButton("Close");

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
                handleException("N should be number");
                return;
            }

            if(n <= 0 || n > 32) {
                handleException("N can't be less then zero or grater then 32");
                return;
            }

            try {
                num = Integer.parseInt(sNum.substring(0, sNum.length() - 1), 8);
            } catch(NumberFormatException exception) {
                handleException("Num should be a polynomial from table in form \"###{A-H}\"");
                return;
            }

            if((num & (1 << n)) == 0 || (num & 1) == 0) {
                handleException("Num_2[0] * Num_2[N] != 1");
                return;
            }

            model.addRow(new String[]{sN, sNum});
        });

        panel.add(bDelete);
        panel.add(bAdd);
        panel.add(bClear);
        panel.add(bClose);
    }

    @Override
    protected void setupMenu() {

    }

    public CustomPolynomialsFrame() {
        super("Custom table", 400, 400, new GridLayout(2, 1));

        loadModel(); // on start of the frame should load data
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void dispose() {
        super.dispose();
        saveModel(); // when JFrame disposed
                     // saves data to file
    }

}
