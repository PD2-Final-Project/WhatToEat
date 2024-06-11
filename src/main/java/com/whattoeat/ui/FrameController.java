package com.whattoeat.ui;

import com.whattoeat.model.StoresDataQuery;
import com.whattoeat.model.processor.Mood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Desktop;
import java.net.URI;

public class FrameController {

    private static int currentIndex = 0;
    private static int totalStores;
    private static String[] storeNames;
    private static int[] storePrice;
    private static int[] storeDistance;
    private static double[] storeRating;
    private static String[] storeUrls;
    private static String[] storeAddress;

    private static Frame2 secondPage;

    public static JFrame frame;

    public static void initialize() {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        int radius = 1000;
        String keyWord = "restaurant";
        String location = "成大";
        Mood mood = Mood.GOOD;
        StoresDataQuery storesDataQuery = new StoresDataQuery(location, keyWord, radius);
        storeNames = storesDataQuery.storesData.getNames();
        storePrice = storesDataQuery.storesData.getPriceLevels();
        storeDistance = storesDataQuery.storesData.getDistances();
        storeRating = storesDataQuery.storesData.getRatings();
        storeUrls = storesDataQuery.storesData.getUrls();
        storeAddress = storesDataQuery.storesData.getAddresses();

        totalStores = storeNames.length;  // Initialize totalStores here

        frame = new JFrame("What to Eat?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new CardLayout());

        Frame1 firstPage = new Frame1();
        secondPage = new Frame2();

        frame.add(firstPage, "First Page");
        frame.add(secondPage, "Second Page");
//        private class updateDateFromFrame1(){
//            radius = 1000;
//            keyWord = "restaurant";
//            location = "成大";
//            mood =
//        }

        firstPage.submitButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
//            updateDateFromFrame1()



            cl.show(frame.getContentPane(), "Second Page");
            updateStoreData();
        });

        secondPage.prevButton.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                System.out.println("Previous button clicked. Current index: " + currentIndex);
                updateStoreData();
            } else {
                System.out.println("Already at the first store. Current index: " + currentIndex);
            }
        });

        secondPage.nextButton.addActionListener(e -> {
            if (currentIndex < totalStores - 1) {
                currentIndex++;
                System.out.println("Next button clicked. Current index: " + currentIndex);
                updateStoreData();
            } else {
                System.out.println("Already at the last store. Current index: " + currentIndex);
            }
        });

        secondPage.backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "First Page");
        });

        frame.setVisible(true);
    }

    private static void updateStoreData() {
        if (currentIndex >= 0 && currentIndex < totalStores) {
            secondPage.storeNameData.setText(storeNames[currentIndex]);
            secondPage.priceData.setText(String.valueOf(storePrice[currentIndex]));
            secondPage.distanceData.setText(String.valueOf(storeDistance[currentIndex]));
            secondPage.ratingData.setText(String.format("%.1f", storeRating[currentIndex]));
            secondPage.uriData.setText(storeUrls[currentIndex]);
            //secondPage.uriData = new HyperlinkLabel(storeUrls[currentIndex]);
            secondPage.indexLabel.setText((currentIndex + 1) + "/" + totalStores);

            System.out.println("Updating store data for index: " + currentIndex);
        } else {
            System.out.println("Invalid index: " + currentIndex);
        }
    }


}
