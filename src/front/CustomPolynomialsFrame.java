package front;

import logic.Polynomial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// TODO Add comments and javadoc
// TODO Create write data method
public class CustomPolynomialsFrame extends JFrame {

    private final String path = "./resource/custom_polynomials.txt";

    private JTable table;

    private List<List<String>> data;
    private final String[] columnNames = new String[]{};

    private void readDataFromFile() {
        data = new ArrayList<>();
        File file = new File(path);
        if(file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                data = (List<List<String>>) objectInputStream.readObject();
                //Polynomial polynomial = (Polynomial) objectInputStream.readObject();
                //data.add(List.of(polynomial.toString().split(" ")));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDataToFile() {
        File file = new File(path);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(data);
//            for (List<String> datum : data) {
//                //noinspection ConstantConditions
//                objectOutputStream.writeObject(new Polynomial((String[]) datum.toArray()));
//            }
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    private static String[][] fromListListToStringMatrix(List<List<String>> l) {
        if(l == null) {
            return null;
        }
        String[][] data = new String[l.size()][3];
        for (int i = 0; i < l.size(); i++) {
            for(int j = 0; j < 3; j++) {
                data[i][j] = l.get(i).get(j);
            }
        }
        return data;
    }

    private void setupUI() {
        table = new JTable(new Object[0][3], columnNames);
        table.setDefaultEditor(Object.class, null);
        table.setFont(LFSRFrame.font);

        setTable();

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
            int[] selected = table.getSelectedRows();
            if(selected != null && selected.length != 0) {
                // delete rows
                for (int i = 0; i < selected.length; i++) data.remove(selected[i] - i);
                // TODO Make sure it works
                // then save
                saveDataToFile();

                // update table
                setTable();
            }
        });
        bAdd.addActionListener(e -> {
            // TODO Create add action
            // getData from user
            String sN = tfN.getText();
            String sNum = tfNum.getText();

            // then make sure they are fine
            int n = -1, num = -1;
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

            // then add new row to data
            data.add(List.of(new String[]{sN, sNum, Integer.toBinaryString(num)}));

            // then save
            saveDataToFile();

            // update table
            setTable();
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

    private void setTable() {

    }

    public CustomPolynomialsFrame() {
        setTitle("Custom table");
        setSize(400, 400);
        setLayout(new GridLayout(2, 1));

        readDataFromFile();
        setupUI();

        setVisible(true);
    }
}
