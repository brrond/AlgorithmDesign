package front;

import logic.Matrix;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

public class MSRSetSeedFrame {

    private JTable table;
    private JPanel mainPanel;
    private JButton setMatrixButton;
    private JButton closeButton;

    private TableModel model;
    private final Matrix matrix;

    private class MSRSetSeedFrameInner extends FrameBase {

        public MSRSetSeedFrameInner() {
            super("MSR set seed", 400, 300, null);
        }

        @Override
        protected void setupUI() {
            model.addTableModelListener(e -> {
                if(e.getType() == TableModelEvent.UPDATE) {
                    int column = e.getColumn();
                    int row = e.getFirstRow();
                    Object valObj = model.getValueAt(row, column);
                    int val = Integer.parseInt(String.valueOf(valObj));
                    if(val < 0 || val > 1) {
                        handleException("Value should be 0 or 1");
                        setMatrixButton.setEnabled(false);
                    } else {
                        setMatrixButton.setEnabled(true);
                    }
                }
            });
        }

        @Override
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

        @Override
        protected void setupMenu() {

        }
    }

    public MSRSetSeedFrame(Matrix matrix) {
        this.matrix = matrix;

        MSRSetSeedFrameInner msrSetSeedFrameInner = new MSRSetSeedFrameInner();
        msrSetSeedFrameInner.setContentPane(mainPanel);

        msrSetSeedFrameInner.setFonts();
    }

    public void createUIComponents() {
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
    }
}
