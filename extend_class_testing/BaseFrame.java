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

        FirstPage firstPage = new FirstPage(this);
        SecondPage secondPage = new SecondPage(this);

        cardPanel.add(firstPage, "First Page");
        cardPanel.add(secondPage, "Second Page");
        add(cardPanel);

        setVisible(true);
    }

    public void showPage(String card) {
        cardLayout.show(cardPanel, card);
    }

    public static void main(String[] args) {
        new BaseFrame();
    }
}
