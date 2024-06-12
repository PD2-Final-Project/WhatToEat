package com.whattoeat.ui;

import com.whattoeat.model.processor.Mood;

import javax.swing.*;
import java.awt.*;

public class Frame1 extends JPanel {
    private final JTextField locationField = new JTextField(15);
    private final JTextField radiusField = new JTextField(15);
    private final JTextField keyWordField = new JTextField(15);
    private final JComboBox<Mood> moodDropdown = new JComboBox<>(Mood.values());
    JButton submitButton = new JButton("Click");

    public Frame1() {
        setLayout(null);
        initUI();

    }

    private void initUI() {

        // Title Panel
        JPanel titlePanel1 = createPanel(350, 50, 300, 50, new Color(173, 216, 230));
        JLabel titleLabel1 = createLabel("What to eat?", Color.WHITE);
        titlePanel1.add(titleLabel1);

        //all the input
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 2, 10, 10));  // 4 rows, 2 columns, with gaps
        infoPanel.setBounds(250, 150, 500, 300);
        infoPanel.setBackground(new Color(173, 216, 230));
        // Adding labels and fields for location, radius, and keyword
        infoPanel.add(new JLabel("所在地點"));
        infoPanel.add(locationField);

        infoPanel.add(new JLabel("半徑"));
        infoPanel.add(radiusField);

        infoPanel.add(new JLabel("keyword"));
        infoPanel.add(keyWordField);

        // Adding label and dropdown for mood
        infoPanel.add(new JLabel("心情"));
        moodDropdown.setPreferredSize(new Dimension(250, 30));
        infoPanel.add(moodDropdown);

        // Submit Button
        submitButton = new JButton("Click!");
        submitButton.setBounds(450, 450, 100, 50);

        // Add components to panel
        add(infoPanel);  // Make sure this is placed correctly within the layout
        add(titlePanel1);
        add(submitButton);

    }

    private JPanel createPanel(int x, int y, int width, int height, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setBounds(x, y, width, height);
        panel.setLayout(new FlowLayout());
        return panel;
    }

    private JLabel createLabel(String text, Color fgColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(fgColor);
        return label;
    }

    public void updateDataFromFrame1() {
        int radius = 0;
        try {
            // 從文本框中讀取數字，並去除前後空白
            radius = Integer.parseInt(radiusField.getText().trim());
            System.out.println("半徑: " + radius);
        } catch (NumberFormatException e) {
            System.out.println("輸入的半徑不是有效的整數");
        }

        // 從文本框中讀取字符串，並去除前後空白
        String keyWord = keyWordField.getText().trim();
        System.out.println("關鍵詞: " + keyWord);

        // 從文本框中讀取字符串，並去除前後空白
        String location = locationField.getText().trim();
        System.out.println("位置: " + location);

        // 從下拉菜單中獲取選擇的項目，假設它是枚舉類型 Mood 的一個實例
        Mood selectedMood = (Mood) moodDropdown.getSelectedItem();
        if (selectedMood != null) {
            System.out.println("選擇的心情: " + selectedMood);
        } else {
            System.out.println("未選擇心情");
        }


        // 假設有一個存儲這些數據的地方，例如 FrameController 或其他管理類
        FrameController.update(location, keyWord, radius, selectedMood);
    }
}
