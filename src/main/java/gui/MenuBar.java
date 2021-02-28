package gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.Expression;

public class MenuBar {
    private final JMenu menu;

    MenuBar(String name, int keyEvent, String description) {
        this.menu = new JMenu(name);
        this.menu.setMnemonic(keyEvent);
        this.menu.getAccessibleContext().setAccessibleDescription(
                description);
    }

    void addMenuBarField(String name, int keyEvent,ActionListener listenerFunction) {

        JMenuItem systemLookAndFeel = new JMenuItem(name, keyEvent);
        systemLookAndFeel.addActionListener(listenerFunction);
        menu.add(systemLookAndFeel);

    }

    JMenu getMenu(){
        return menu;
    }

}
