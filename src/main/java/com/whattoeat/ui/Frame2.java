package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Frame2 extends JPanel {
    public JLabel storeNameData;
    public JLabel priceData;
    public JLabel distanceData;
    public JLabel ratingData;
    public JLabel uriData;
    public JLabel indexLabel;
    public JButton prevButton;
    public JButton nextButton;
    public JButton backButton;
    private JLabel imageLabel;

    public Frame2() {
        setLayout(null);
        initUI();
    }

    private void initUI() {
        // Title Panel
        JPanel titlePanel2 = createPanel(350, 20, 300, 50, new Color(70, 130, 180));
        JLabel titleLabel2 = createLabel("What to eat?", Color.WHITE);
        titleLabel2.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel2.add(titleLabel2);

        // Image Panel
        JPanel imagePanel = createPanel(570, 100, 390, 370, new Color(224, 255, 255));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        imageLabel = new JLabel("Google Map Image", SwingConstants.CENTER);
        imageLabel.setForeground(Color.BLACK);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(imageLabel);

        // Store Information Panel
        JPanel storeInfoPanel = new JPanel();
        storeInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        storeInfoPanel.setBounds(50, 100, 500, 300);
        storeInfoPanel.setBackground(new Color(224, 255, 255));
        storeInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Store Information Labels and Data
        addStoreInfo(storeInfoPanel, "店名", storeNameData = new JLabel("Store Name Data"), true);
        addStoreInfo(storeInfoPanel, "價格", priceData = new JLabel("Average Price Data"), true);
        addStoreInfo(storeInfoPanel, "距離", distanceData = new JLabel("storeDistance"), true);
        addStoreInfo(storeInfoPanel, "Google評分", ratingData = new JLabel("Rating Data"), true);
        addStoreInfo(storeInfoPanel, "Google 網址", uriData = new HyperlinkLabel("URI"), true);

        // Index Label
        indexLabel = createLabel("0/0", Color.BLACK);
        indexLabel.setBounds(450, 420, 100, 30);

        // Navigation Buttons
        prevButton = createButton("←", 200, 500);
        nextButton = createButton("→", 700, 500);
        backButton = createButton("Back", 450, 500);

        // Add components to panel
        add(indexLabel);
        add(titlePanel2);
        add(imagePanel);
        add(storeInfoPanel);
        add(prevButton);
        add(nextButton);
        add(backButton);
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

    private void addStoreInfo(JPanel parentPanel, String labelText, JLabel dataLabel, boolean chinese) {
        JPanel labelPanel = createPanel(0, 0, 200, 50, new Color(224, 255, 255));
        labelPanel.add(createLabel(labelText, Color.BLACK, chinese));
        JPanel dataPanel = createPanel(0, 0, 300, 50, new Color(224, 255, 255));
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dataLabel.setFont(new Font(chinese ? "Microsoft YaHei" : "Arial", Font.PLAIN, 16));
        dataPanel.add(dataLabel);
        parentPanel.add(labelPanel);
        parentPanel.add(dataPanel);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 50);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    public void setImageFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText("");
        } catch (IOException e) {
            e.printStackTrace();
            imageLabel.setText("Image Load Failed");
        }
    }
}
