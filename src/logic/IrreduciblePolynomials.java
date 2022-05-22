package logic;

import front.CustomPolynomialsFrame;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public final class IrreduciblePolynomials {

    /**
     * Method to get all irreducible polynomials
     *
     * @return {@code String[]} of irreducible polynomials
     */
    public static String[] getListData() {
        List<String> output = new java.util.ArrayList<>(List.of("^2 7H",
                "^3 13F",
                "^4 23F", "^4 37D",
                "^5 45E", "^5 75G", "^5 67H",
                "^6 103F", "^6 127B", "^6 147H", "^6 111A", "^6 155E",
                "^7 211E", "^7 217E", "^7 235E", "^7 367H", "^7 277E",
                "^8 435E", "^8 567B", "^8 763D", "^8 551E", "^8 675C",
                "^9 1021E", "^9 1131E", "^9 1451G", "^9 1231A", "^9 1423G",
                "^10 2011E", "^10 2017B", "^10 3771G", "^10 2357B",
                "^11 4005E", "^11 4445E", "^11 4215E", "^11 4055E", "^11 6015G",
                "^12 10123F", "^12 12133B", "^12 10115A", "^12 12153B", "^12 11765A",
                "^13 20033F", "^13 23261E", "^13 24623F", "^13 23517F", "^13 30741G",
                "^14 47103", "^14 40547B", "^14 43333E", "^14 51761E", "^14 54055A"));

        File file = new File(CustomPolynomialsFrame.path);
        TableModel model = null;
        if(file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                model = (TableModel) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(model != null) {
            for(int i = 0; i < model.getRowCount(); i++) {
                String pow = (String) model.getValueAt(i, 0);
                String num = (String) model.getValueAt(i, 1);
                output.add("^" + pow + " " + num);
            }
        }

        return output.toArray(new String[0]);
    }

}
