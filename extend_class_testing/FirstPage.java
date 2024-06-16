import javax.swing.*;
import java.awt.*;

public class FirstPage extends JPanel {
    public FirstPage(BaseFrame frame) {
        setLayout(null);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(173, 216, 230));
        titlePanel.setBounds(350, 50, 300, 50);
        JLabel titleLabel = new JLabel("What to eat?", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        JComboBox<String> dropdown = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        JButton submitButton = new JButton("Click!");
        submitButton.setBounds(750, 450, 50, 50);

        add(titlePanel);
        add(dropdown);
        add(submitButton);

        submitButton.addActionListener(e -> frame.showPage("Second Page"));
    }
}
