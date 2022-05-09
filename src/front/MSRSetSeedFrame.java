package front;

import logic.Matrix;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

public class MSRSetSeedFrame extends JFrame {

    private JTable table;
    private JPanel mainPanel;
    private JButton setMatrixButton;
    private JButton closeButton;

    private TableModel model;
    private Matrix matrix;

    protected void setupButtons() {
        setMatrixButton.addActionListener(e -> {
            for(int i = 0; i < matrix.getN(); i++) {
                for (int j = 0; j < matrix.getM(); j++) {
                    int val = Integer.parseInt((String) model.getValueAt(i, j));
                    matrix.set(i, j, (double) val);
                }
            }
            dispose();
        });

        closeButton.addActionListener(e -> dispose());
    }

    public MSRSetSeedFrame(Matrix matrix) {
        this.matrix = matrix;

        setSize(400, 480);
        setResizable(false);
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setupButtons();

        setVisible(true);
    }

    private void createUIComponents() {
         String[][] data = new String[matrix.getN()][matrix.getM()];
         String[] columns = new String[matrix.getM()];
         for(int i = 0; i < matrix.getN(); i++) {
             for(int j = 0; j < matrix.getM(); j++) {
                columns[j] = String.valueOf(j);
                 data[i][j] = String.valueOf(Math.round(matrix.get(i, j)));
             }
         }

         table = new JTable(data, columns);
         model = table.getModel();
         model.addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE) {
                int column = e.getColumn();
                int row = e.getFirstRow();
                Object valObj = model.getValueAt(row, column);
                int val = Integer.parseInt(String.valueOf(valObj));
                if(val < 0 || val > 1) {
                    // TODO Insert error msg here and make Set matrix button not active
                    setMatrixButton.setEnabled(false);
                } else {
                    setMatrixButton.setEnabled(true);
                }
            }
         });
    }
}
