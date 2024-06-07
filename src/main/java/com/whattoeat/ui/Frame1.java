package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;

public class Frame1 extends JPanel{
    public JTextField inputField;
    public JComboBox<String> dropDown;
    public JButton submitButton;

    public Frame1() {
        setLayout(null);

    }

    private void initUI() {
        JPanel titlePanel = createPanel(350, 250, 300, 50, new Color(173,216,230));
        JLabel titleLable = createLabel("What to eat?", Color.WHITE);
        titlePanel.add(titleLable);

        JPanel dropdownPanel = createPanel(350, 250, 300, 50, new Color(173, 216, 230));
        dropDown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        dropDown.setPreferredSize(new Dimension(250, 30));
        JButton dropdownButton = new JButton("▼");
        dropdownButton.setPreferredSize(new Dimension(50, 50));
        dropdownPanel.add(dropDown);
        dropdownPanel.add(dropdownButton);

        // Input Field
        inputField = new JTextField();
        inputField.setBounds(250, 450, 500, 50);

        // Submit Button
        submitButton = new JButton("Click!");
        submitButton.setBounds(750, 450, 50, 50);

        add(titlePanel);
        add(dropdownPanel);
        add(inputField);
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
