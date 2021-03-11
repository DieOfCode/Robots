package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import log.Logger;
import model.Robot;

import static gui.GameWindow.createGameWindow;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);


        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource()).createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow(new Robot());
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }




    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        MenuBar lookAndFeelMenu = new MenuBar("Режим отображения", KeyEvent.VK_V, "Управление режимом отображения приложения");
        lookAndFeelMenu.addMenuBarField("Системная схема", KeyEvent.VK_S, getActionListenerForUI(UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.addMenuBarField("Системная схема", KeyEvent.VK_S, getActionListenerForUI(UIManager.getSystemLookAndFeelClassName()));
        lookAndFeelMenu.addMenuBarField("Универсальная схема", KeyEvent.VK_S, getActionListenerForUI(UIManager.getCrossPlatformLookAndFeelClassName()));

        MenuBar mainMenu = new MenuBar("Основное меню", KeyEvent.VK_M, "Взаимодействие с основным функционалом");
        mainMenu.addMenuBarField("Выход", 0, (event) -> {
            System.exit(0);
        });


        MenuBar testMenu = new MenuBar("Тесты", KeyEvent.VK_T, "Тестовые команды");
        testMenu.addMenuBarField("Сообщение в лог", KeyEvent.VK_S, (event) -> {
            Logger.debug("Новая строка");
        });

        menuBar.add(mainMenu.getMenu());
        menuBar.add(lookAndFeelMenu.getMenu());
        menuBar.add(testMenu.getMenu());
        return menuBar;
    }

    private ActionListener getActionListenerForUI(String crossPlatformLookAndFeelClassName) {
        return (event) -> {
            setLookAndFeel(crossPlatformLookAndFeelClassName);
            this.invalidate();
        };
    }


    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
