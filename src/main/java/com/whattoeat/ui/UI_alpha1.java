package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;

public class UI_alpha1 {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Set new frame
        JFrame frame = new JFrame("What to eat?");

        // Set exit and basic Interface
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);
        frame.setLayout(new CardLayout());

        // Frist page
        JPanel EnterPage = EnterPage(frame,new Color(173,216,230),Color.WHITE);
        JPanel DataPage = dataPage(frame,new Color(173,216,230),Color.WHITE);

        frame.add(EnterPage,"Enter Page");
        frame.add(DataPage,"Data Page");

        frame.setVisible(true);

    }

    private static JPanel EnterPage(JFrame frame , Color objColor , Color wordColor) {
        JPanel Panelpage = new JPanel();
        Panelpage.setLayout(null);

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(objColor);
        titlePanel.setBounds(350,50,300,50);
        JLabel titleLabel = new JLabel("what to eat?" , SwingConstants.CENTER);
        // Word Color (Label)
        titleLabel.setForeground(wordColor);
        titlePanel.add(titleLabel);

        // Dropdown Panel
        JPanel dropDownPanel = new JPanel();
        dropDownPanel.setBackground(objColor);
        dropDownPanel.setBounds(350,250,300,70);
        JComboBox<String> options = new JComboBox<>(new String[]{"1","2","3"});
        options.setPreferredSize(new Dimension(250,30));
        JButton dropdownButton = new JButton("▼");
        dropdownButton.setPreferredSize(new Dimension(50,30));
        dropDownPanel.add(options);
        dropDownPanel.add(dropdownButton);

        // Input Obj
        JTextField input = new JTextField();
        input.setBounds(350,450,300,50);

        // Submit Button
        JButton sumitBotton = new JButton("click");
        sumitBotton.setBounds(650,450,70,50);
        sumitBotton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(),"Data Page");
        });

        // Add
        Panelpage.add(titlePanel);
        Panelpage.add(dropDownPanel);
        Panelpage.add(input);
        Panelpage.add(sumitBotton);

        return Panelpage;
    }

    private static JPanel dataPage(JFrame frame,Color objColor,Color wordColor) {
        JPanel dataPage = new JPanel();
        dataPage.setLayout(null);

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(objColor);
        titlePanel.setBounds(350,20,300,50);

        JLabel titleLabel = new JLabel("What to eat?",SwingConstants.CENTER);
        titleLabel.setForeground(wordColor);
        titlePanel.add(titleLabel);

        // Set image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(objColor);
        imagePanel.setBounds(600,100,300,300);

        JLabel imageLabel = new JLabel("Google Map Image",SwingConstants.CENTER);
        imageLabel.setForeground(wordColor);
        imagePanel.add(imageLabel);

        // Store Information
        JPanel storeInfoPanel = storeInfo();

        // Navigation Bottons
        JButton prevBtn = new JButton("<-");
        prevBtn.setBounds(200,450,100,50);
        prevBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(),"Enter Page");
        });

        JButton nextBtn = new JButton("->");
        nextBtn.setBounds(700,450,100,50);
        nextBtn.addActionListener(e -> {
            // Update Info
            // Need to replace another logic
            updateInfo(storeInfoPanel,"1","2","3","4","5");
        });

        dataPage.add(titlePanel);
        dataPage.add(imagePanel);
        dataPage.add(storeInfoPanel);
        dataPage.add(prevBtn);
        dataPage.add(nextBtn);

        return dataPage;
    }

    private static JPanel storeInfo() {
        JPanel storeInfoPanel = new JPanel();
        storeInfoPanel.setLayout((new GridLayout(5,2,10,10)));
        storeInfoPanel.setBounds(50,100,500,300);

        // Store Name
        storeInfoPanel.add(createLabelPanel("店名"));
        storeInfoPanel.add(createDataPanel("Store Name Data"));

        // Average Price
        storeInfoPanel.add(createLabelPanel("價格"));
        storeInfoPanel.add(createDataPanel("Average Price Data"));

        // Distance
        storeInfoPanel.add(createLabelPanel("距離"));
        storeInfoPanel.add(createDataPanel("Distance Data"));

        // Rating
        storeInfoPanel.add(createLabelPanel("Google評分"));
        storeInfoPanel.add(createDataPanel("Rating Data"));

        // URI
        storeInfoPanel.add(createLabelPanel("Google 網址"));
        storeInfoPanel.add(createDataPanel("URI Data"));

        return storeInfoPanel;
    }

    private static JPanel createLabelPanel(String text) {
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(173, 216, 230));
        labelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        labelPanel.add(label);
        return labelPanel;
    }

    private static JPanel createDataPanel(String text) {
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(new Color(173, 216, 230));
        dataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        dataPanel.add(label);
        return dataPanel;
    }

    private static void updateInfo(JPanel dataInfoPanel, String name, String price, String distance, String rating, String url) {
        ((JLabel) ((JPanel) dataInfoPanel.getComponent(1)).getComponent(0)).setText(name);
        ((JLabel) ((JPanel) dataInfoPanel.getComponent(3)).getComponent(0)).setText(price);
        ((JLabel) ((JPanel) dataInfoPanel.getComponent(5)).getComponent(0)).setText(distance);
        ((JLabel) ((JPanel) dataInfoPanel.getComponent(7)).getComponent(0)).setText(rating);
        ((JLabel) ((JPanel) dataInfoPanel.getComponent(9)).getComponent(0)).setText(url);
    }

}