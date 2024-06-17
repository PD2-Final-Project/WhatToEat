package com.whattoeat.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;

public class HyperlinkLabel extends JLabel {
    private String url;
    private final Logger logger = LogManager.getLogger(HyperlinkLabel.class);
    public HyperlinkLabel(String text) {
        super();
        this.url = text;
        updateText();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.BLUE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        });
    }

    @Override
    public void setText(String text) {
        this.url = text;
        updateText();
    }

    private void updateText() {
        super.setText("<html><a href='' style='color: blue;'>" + "點擊這裡" + "</a></html>");
    }
}
