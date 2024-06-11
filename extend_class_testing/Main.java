import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the look and feel to the system look and feel to make the UI consistent with the operating system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Schedule a job for the event-dispatching thread:
        // Creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> {
            new BaseFrame(); // Create and show the main application frame
        });
    }
}
