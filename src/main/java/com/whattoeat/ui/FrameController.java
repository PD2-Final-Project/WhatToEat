package com.whattoeat.ui;

import com.whattoeat.model.StoresDataQuery;

import javax.swing.*;
import java.awt.*;

public class FrameController {
    public static void main(String[] args) {
        int radius = 1000;
        String keyWord = "restaurant";
        String location = "成大";
        StoresDataQuery storesDataQuery = new StoresDataQuery(location, keyWord, radius);

        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("What to Eat?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new CardLayout());

        Frame1 firstPage = new Frame1();
        Frame2 secondPage = new Frame2();

        frame.add(firstPage, "First Page");
        frame.add(secondPage, "Second Page");

        // Navigation between pages
        firstPage.submitButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Second Page");
        });

        // Maybe have other method?
        secondPage.prevButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "First Page");
        });

        secondPage.nextButton.addActionListener(e -> {
            // Update Info
            updateStoreData(secondPage, storesDataQuery);
        });

        frame.setVisible(true);
    }

    private static void updateStoreData(Frame2 secondPage, StoresDataQuery storesDataQuery) {
        // Uncompleted
        secondPage.storeNameData.setText("Next Store Name");
        secondPage.priceData.setText("Next Average Price");
        secondPage.distanceData.setText("Next Distance");
        secondPage.ratingData.setText("Next Rating");
        secondPage.uriData.setText("Next URI");
        // pic ?
    }
}
