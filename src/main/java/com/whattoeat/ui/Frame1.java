package com.whattoeat.ui;

import com.whattoeat.model.processor.Mood;

import javax.swing.*;
import java.awt.*;

public class Frame1 extends JPanel {
    public JTextField locationField = new JTextField(15);
    public JTextField radiusField = new JTextField(15);
    public JTextField keyWordField = new JTextField(15);
    public JComboBox<Mood> moodDropdown = new JComboBox<>(Mood.values());
    JButton submitButton = new JButton("Click");

    public Frame1() {
        setLayout(null);
        initUI();
    }

    private void initUI() {
        // Title Panel
        JPanel titlePanel1 = createPanel(350, 50, 300, 50, new Color(70, 130, 180));
        JLabel titleLabel1 = createLabel("What to eat?", Color.WHITE);
        titleLabel1.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel1.add(titleLabel1);

        // All the input
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 2, 10, 10));
        infoPanel.setBounds(250, 150, 500, 300);
        infoPanel.setBackground(new Color(224, 255, 255));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Adding labels and fields for location, radius, and keyword
        infoPanel.add(createLabel("所在地點", Color.BLACK, true));
        infoPanel.add(locationField);

        infoPanel.add(createLabel("半徑", Color.BLACK, true));
        infoPanel.add(radiusField);

        infoPanel.add(createLabel("Keyword", Color.BLACK, true));
        infoPanel.add(keyWordField);

        // Adding label and dropdown for mood
        infoPanel.add(createLabel("心情", Color.BLACK, true));
        moodDropdown.setPreferredSize(new Dimension(250, 30));
        infoPanel.add(moodDropdown);

        // Submit Button
        submitButton = new JButton("Click!");
        submitButton.setBounds(450, 500, 100, 50);
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.black);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Add components to panel
        add(infoPanel);
        add(titlePanel1);
        add(submitButton);
    }

    private JPanel createPanel(int x, int y, int width, int height, Color bgColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setBounds(x, y, width, height);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        return panel;
    }

    private JLabel createLabel(String text, Color fgColor) {
        return createLabel(text, fgColor, false);
    }

    private JLabel createLabel(String text, Color fgColor, boolean chinese) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(fgColor);
        label.setFont(new Font(chinese ? "Microsoft YaHei" : "Arial", Font.PLAIN, 16));
        return label;
    }

    public void updateDataFromFrame1() {
        int radius = 0;
        try {
            radius = Integer.parseInt(radiusField.getText().trim());
            System.out.println("半徑: " + radius);
        } catch (NumberFormatException e) {
            System.out.println("輸入的半徑不是有效的整數");
        }

        String keyWord = keyWordField.getText().trim();
        System.out.println("關鍵詞: " + keyWord);

        String location = locationField.getText().trim();
        System.out.println("位置: " + location);

        Mood selectedMood = (Mood) moodDropdown.getSelectedItem();
        if (selectedMood != null) {
            System.out.println("選擇的心情: " + selectedMood);
        } else {
            System.out.println("未選擇心情");
        }

        FrameController.update(location, keyWord, radius, selectedMood);
    }
}
