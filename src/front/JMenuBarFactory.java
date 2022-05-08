package front;

import javax.swing.*;
import java.awt.event.ActionListener;

public final class JMenuBarFactory {

    public static JMenuBar createJMenuBar(String[] menus, String[][] menuItems, ActionListener[][] actions) {
        if(menus == null || menus.length == 0) throw new IllegalArgumentException("menus can't be empty array");
        if(menuItems == null || menuItems.length == 0) throw new IllegalArgumentException("menuItems can't be empty array");
        if(actions == null || actions.length == 0) throw new IllegalArgumentException("actions can't be empty array");
        if(menus.length != menuItems.length) throw new IllegalArgumentException("menus.length should be equal to menuItems.length");

        JMenuBar menuBar = new JMenuBar();
        for(int i = 0; i < menus.length; i++) {
            JMenu menu = new JMenu(menus[i]);
            for(int j = 0; j < menuItems[i].length; j++) {
                JMenuItem item = new JMenuItem(menuItems[i][j]);
                item.addActionListener(actions[i][j]);
                menu.add(item);
            }
            menuBar.add(menu);
        }

        return menuBar;
    }

}
