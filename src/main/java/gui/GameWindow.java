package gui;

import model.Robot;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import static gui.PopUpPanel.closingPanelLogic;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;

    public GameWindow(Robot robot)
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer(robot);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }



    public  static GameWindow createGameWindow(Robot robot) {
        robot.setSize(400, 400);

        GameWindow gameWindow = new GameWindow(robot);

        gameWindow.setSize(400, 400);

        gameWindow.addInternalFrameListener(
                new InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosing(InternalFrameEvent event) {
                        super.internalFrameClosing(event);
                        closingPanelLogic(event);
                    }
                }
        );

        gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                robot.setSize(gameWindow.getWidth(), gameWindow.getHeight());

            }
        });
        return gameWindow;
    }

}
