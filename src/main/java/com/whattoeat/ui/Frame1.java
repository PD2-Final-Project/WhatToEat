package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;

public class Frame1 extends JPanel {
    public JTextField inputField;
    public JComboBox<String> dropdown;
    public JButton submitButton;

    public Frame1() {
        setLayout(null);
        initUI();
    }

    private void initUI() {
        // Title Panel
        JPanel titlePanel1 = createPanel(350, 50, 300, 50, new Color(173, 216, 230));
        JLabel titleLabel1 = createLabel("What to eat?", Color.WHITE);
        titlePanel1.add(titleLabel1);

//        // Dropdown Panel
//        JPanel dropdownPanel = createPanel(350, 250, 300, 85, new Color(173, 216, 230));
//        dropdown = new JComboBox<>(new String[]{"NORMAL", "GOOD", "BAD"});
//        dropdown.setPreferredSize(new Dimension(250, 30));
//        JButton dropdownButton = new JButton("▼");
//        dropdownButton.setPreferredSize(new Dimension(50, 40));
//        dropdownPanel.add(dropdown);
//        dropdownPanel.add(dropdownButton);

        //try
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 2, 10, 10));  // 4 rows, 2 columns, with gaps
        infoPanel.setBounds(250, 150, 500, 300);
        infoPanel.setBackground(new Color(173, 216, 230));
        // Adding labels and fields for location, radius, and keyword
        infoPanel.add(new JLabel("所在地點"));
        infoPanel.add(new JTextField(15));

        infoPanel.add(new JLabel("半徑"));
        infoPanel.add(new JTextField(15));

        infoPanel.add(new JLabel("keyword"));
        infoPanel.add(new JTextField(15));

        // Adding label and dropdown for mood
        infoPanel.add(new JLabel("心情"));
        JComboBox<String> moodDropdown = new JComboBox<>(new String[]{"NORMAL", "GOOD", "BAD"});
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
}
