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
        JPanel titlePanel2 = createPanel(350, 20, 300, 50, new Color(173, 216, 230));
        JLabel titleLabel2 = createLabel("What to eat?", Color.WHITE);
        titlePanel2.add(titleLabel2);

        // Image Panel
        JPanel imagePanel = createPanel(600, 100, 350, 350, new Color(173, 216, 230));
        imageLabel = new JLabel("Google Map Image", SwingConstants.CENTER);
        imageLabel.setForeground(Color.WHITE);
        imagePanel.add(imageLabel);

        // Store Information Panel
        JPanel storeInfoPanel = new JPanel();
        storeInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        storeInfoPanel.setBounds(50, 100, 500, 300);
        storeInfoPanel.setBackground(new Color(173, 216, 230));

        // Store Information Labels and Data
        addStoreInfo(storeInfoPanel, "店名", storeNameData = new JLabel("Store Name Data"));
        addStoreInfo(storeInfoPanel, "價格", priceData = new JLabel("Average Price Data"));
        addStoreInfo(storeInfoPanel, "距離", distanceData = new JLabel("storeDistance"));
        addStoreInfo(storeInfoPanel, "Google評分", ratingData = new JLabel("Rating Data"));
        addStoreInfo(storeInfoPanel, "Google 網址", uriData = new HyperlinkLabel("URI"));

        // Index Label
        indexLabel = createLabel("0/0", Color.BLACK);
        indexLabel.setBounds(450, 400, 100, 30);

        // Navigation Buttons
        prevButton = new JButton("←");
        prevButton.setBounds(200, 450, 100, 50);

        nextButton = new JButton("→");
        nextButton.setBounds(700, 450, 100, 50);

        backButton = new JButton("Back");
        backButton.setBounds(450, 450, 100, 50);

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
        panel.setLayout(new FlowLayout());
        return panel;
    }

    private JLabel createLabel(String text, Color fgColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(fgColor);
        return label;
    }

    private void addStoreInfo(JPanel parentPanel, String labelText, JLabel dataLabel) {
        JPanel labelPanel = createPanel(0, 0, 200, 50, new Color(173, 216, 230));
        labelPanel.add(createLabel(labelText, Color.BLACK));
        JPanel dataPanel = createPanel(0, 0, 300, 50, new Color(173, 216, 230));
        dataLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dataPanel.add(dataLabel);
        parentPanel.add(labelPanel);
        parentPanel.add(dataPanel);
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
