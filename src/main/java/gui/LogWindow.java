package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import log.Logger;

import static gui.PopUpPanel.closingPanelLogic;

public class LogWindow extends JInternalFrame implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }


    public LogWindowSource getLogWindowSource() {
        return m_logSource;
    }

    private void setClosable() {

    }


    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    private void unregisterListener() {
        m_logSource.unregisterListener(this);
    }

    protected LogWindow createLogWindow() {

        this.setLocation(10, 10);
        this.setSize(300, 800);
        setMinimumSize(this.getSize());
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                closingPanelLogic(event);
                unregisterListener();

            }
        });
        this.pack();
        Logger.debug("Протокол работает");
        return this;
    }
}
