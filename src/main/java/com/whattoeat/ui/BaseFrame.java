package com.whattoeat.ui;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

    public BaseFrame() {
        setTitle("What to Eat?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create the pages
        FirstPage firstPage = new FirstPage(this);
        SecondPage secondPage = new SecondPage(this);

        // Add pages to CardLayout
        cardPanel.add(firstPage, "First Page");
        cardPanel.add(secondPage, "Second Page");

        // Add the card panel to the frame
        add(cardPanel);

        // Make the frame visible
        setVisible(true);
    }

    public void showPage(String card) {
        cardLayout.show(cardPanel, card);
    }
}

