package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;

public class HyperlinkLabel extends JLabel {
    private String url;  // 存储实际的 URL

    public HyperlinkLabel(String text) {
        super();
        this.url = text;  // 存储文本，这里假设文本就是一个有效的 URL
        updateText();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.BLUE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));  // 使用存储的 URL 打开浏览器
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setText(String text) {
        this.url = text;  // 更新 URL
        updateText();  // 更新显示的文本
    }

    private void updateText() {
        super.setText("<html><a href='' style='color: blue;'>" + url + "</a></html>");  // 更新 HTML 内容以显示超链接
    }
}
