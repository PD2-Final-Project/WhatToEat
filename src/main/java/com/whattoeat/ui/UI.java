import com.whattoeat.model.StoresDataQuery;

import javax.swing.*;
import java.awt.*;

public class UI {
    public static void main(String[] args) {

        int radius = 1000;
        String keyWord = "restaurant";
        String location = "成大";
        StoresDataQuery storesDataQuery = new StoresDataQuery(location, keyWord, radius);

        JFrame frame = new JFrame("What to Eat?");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new CardLayout());

        // First Page
        JPanel firstPage = new JPanel();
        firstPage.setLayout(null);

        JPanel titlePanel1 = new JPanel();
        titlePanel1.setBackground(new Color(173, 216, 230));
        titlePanel1.setBounds(350, 50, 300, 50);
        JLabel titleLabel1 = new JLabel("What to eat?", SwingConstants.CENTER);
        titleLabel1.setForeground(Color.WHITE);
        titlePanel1.add(titleLabel1);

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setBackground(new Color(173, 216, 230));
        dropdownPanel.setBounds(350, 250, 300, 50);
        JComboBox<String> dropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        dropdown.setPreferredSize(new Dimension(250, 30));
        JButton dropdownButton = new JButton("▼");
        dropdownButton.setPreferredSize(new Dimension(50, 50));
        dropdownPanel.add(dropdown);
        dropdownPanel.add(dropdownButton);

        JTextField inputField = new JTextField();
        inputField.setBounds(250, 450, 500, 50);

        JButton submitButton = new JButton("Click!");
        submitButton.setBounds(750, 450, 50, 50);

        firstPage.add(titlePanel1);
        firstPage.add(dropdownPanel);
        firstPage.add(inputField);
        firstPage.add(submitButton);

        // Second Page
        JPanel secondPage = new JPanel();
        secondPage.setLayout(null);

        JPanel titlePanel2 = new JPanel();
        titlePanel2.setBackground(new Color(173, 216, 230));
        titlePanel2.setBounds(350, 20, 300, 50);
        JLabel titleLabel2 = new JLabel("What to eat?", SwingConstants.CENTER);
        titleLabel2.setForeground(Color.WHITE);
        titlePanel2.add(titleLabel2);

        // Google Map Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(173, 216, 230));
        imagePanel.setBounds(600, 100, 300, 400);
        JLabel imageLabel = new JLabel("Google Map Image", SwingConstants.CENTER);
        imageLabel.setForeground(Color.WHITE);
        imagePanel.add(imageLabel);

        // Store Information Panels
        JPanel storeInfoPanel = new JPanel();
        storeInfoPanel.setLayout(new GridLayout(5, 2, 10, 10));
        storeInfoPanel.setBounds(50, 100, 500, 300);

        // Store Name
        JPanel storeNameLabelPanel = new JPanel();
        storeNameLabelPanel.setBackground(new Color(173, 216, 230));
        storeNameLabelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel storeNameLabel = new JLabel("店名", SwingConstants.CENTER);
        storeNameLabelPanel.add(storeNameLabel);

        JPanel storeNameDataPanel = new JPanel();
        storeNameDataPanel.setBackground(new Color(173, 216, 230));
        storeNameDataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel storeNameData = new JLabel("Store Name Data", SwingConstants.CENTER);
        storeNameDataPanel.add(storeNameData);

        // Average Price
        JPanel priceLabelPanel = new JPanel();
        priceLabelPanel.setBackground(new Color(173, 216, 230));
        priceLabelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel priceLabel = new JLabel("價格", SwingConstants.CENTER);
        priceLabelPanel.add(priceLabel);

        JPanel priceDataPanel = new JPanel();
        priceDataPanel.setBackground(new Color(173, 216, 230));
        priceDataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel priceData = new JLabel("Average Price Data", SwingConstants.CENTER);
        priceDataPanel.add(priceData);

        // Distance
        JPanel distanceLabelPanel = new JPanel();
        distanceLabelPanel.setBackground(new Color(173, 216, 230));
        distanceLabelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel distanceLabel = new JLabel("距離", SwingConstants.CENTER);
        distanceLabelPanel.add(distanceLabel);

        JPanel distanceDataPanel = new JPanel();
        distanceDataPanel.setBackground(new Color(173, 216, 230));
        distanceDataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel distanceData = new JLabel("storeDistance", SwingConstants.CENTER);
        distanceDataPanel.add(distanceData);

        // Rating
        JPanel ratingLabelPanel = new JPanel();
        ratingLabelPanel.setBackground(new Color(173, 216, 230));
        ratingLabelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel ratingLabel = new JLabel("Google評分", SwingConstants.CENTER);
        ratingLabelPanel.add(ratingLabel);

        JPanel ratingDataPanel = new JPanel();
        ratingDataPanel.setBackground(new Color(173, 216, 230));
        ratingDataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel ratingData = new JLabel("Rating Data", SwingConstants.CENTER);
        ratingDataPanel.add(ratingData);

        // URI
        JPanel uriLabelPanel = new JPanel();
        uriLabelPanel.setBackground(new Color(173, 216, 230));
        uriLabelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel uriLabel = new JLabel("Google 網址", SwingConstants.CENTER);
        uriLabelPanel.add(uriLabel);

        JPanel uriDataPanel = new JPanel();
        uriDataPanel.setBackground(new Color(173, 216, 230));
        uriDataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel uriData = new JLabel("URI Data", SwingConstants.CENTER);
        uriDataPanel.add(uriData);

        // Add all info panels to storeInfoPanel
        storeInfoPanel.add(storeNameLabelPanel);
        storeInfoPanel.add(storeNameDataPanel);
        storeInfoPanel.add(priceLabelPanel);
        storeInfoPanel.add(priceDataPanel);
        storeInfoPanel.add(distanceLabelPanel);
        storeInfoPanel.add(distanceDataPanel);
        storeInfoPanel.add(ratingLabelPanel);
        storeInfoPanel.add(ratingDataPanel);
        storeInfoPanel.add(uriLabelPanel);
        storeInfoPanel.add(uriDataPanel);

        // Navigation buttons
        JButton prevButton = new JButton("←");
        prevButton.setBounds(200, 450, 100, 50);
        JButton nextButton = new JButton("→");
        nextButton.setBounds(700, 450, 100, 50);

        secondPage.add(titlePanel2);
        secondPage.add(imagePanel);
        secondPage.add(storeInfoPanel);
        secondPage.add(prevButton);
        secondPage.add(nextButton);

        frame.add(firstPage, "First Page");
        frame.add(secondPage, "Second Page");

        submitButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "Second Page");
        });

        prevButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
            cl.show(frame.getContentPane(), "First Page");
        });

        nextButton.addActionListener(e -> {
            // Here you should add logic to load the next store's data
            // For example:
            storeNameData.setText("Next Store Name");
            priceData.setText("Next Average Price");
            distanceData.setText("Next Distance");
            ratingData.setText("Next Rating");
            uriData.setText("Next URI");
            // And update the image as well
        });

        frame.setVisible(true);
    }
}