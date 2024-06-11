import javax.swing.*;
import java.awt.*;

public class SecondPage extends JPanel {
    JLabel storeNameData, priceData, distanceData, ratingData, uriData;

    public SecondPage(BaseFrame frame) {
        setLayout(null);

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230));
        titlePanel.setBounds(350, 20, 300, 50);
        JLabel titleLabel = new JLabel("Results", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Google Map image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(173, 216, 230));
        imagePanel.setBounds(600, 100, 300, 400);
        JLabel imageLabel = new JLabel("Google Map Image", SwingConstants.CENTER);
        imageLabel.setForeground(Color.WHITE);
        imagePanel.add(imageLabel);

        // Store information panel
        JPanel storeInfoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        storeInfoPanel.setBounds(50, 100, 500, 300);

        // Adding components to storeInfoPanel
        addInfoComponent(storeInfoPanel, "Store Name", "Store Name Data");
        addInfoComponent(storeInfoPanel, "Average Price", "Average Price Data");
        addInfoComponent(storeInfoPanel, "Distance", "Distance Data");
        addInfoComponent(storeInfoPanel, "Rating", "Rating Data");
        addInfoComponent(storeInfoPanel, "Google URI", "URI Data");

        // Navigation buttons
        JButton prevButton = new JButton("←");
        prevButton.setBounds(200, 450, 100, 50);
        JButton nextButton = new JButton("→");
        nextButton.setBounds(700, 450, 100, 50);

        // Add components to this panel
        add(titlePanel);
        add(imagePanel);
        add(storeInfoPanel);
        add(prevButton);
        add(nextButton);

        prevButton.addActionListener(e -> frame.showPage("First Page"));
        nextButton.addActionListener(e -> {
            // Logic to load the next store's data
        });
    }

    private void addInfoComponent(JPanel panel, String label, String dataText) {
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(173, 216, 230));
        labelPanel.setPreferredSize(new Dimension(200, 50));
        JLabel nameLabel = new JLabel(label, SwingConstants.CENTER);
        labelPanel.add(nameLabel);

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(new Color(173, 216, 230));
        dataPanel.setPreferredSize(new Dimension(300, 50));
        JLabel dataLabel = new JLabel(dataText, SwingConstants.CENTER);
        dataPanel.add(dataLabel);

        panel.add(labelPanel);
        panel.add(dataPanel);
    }
}
