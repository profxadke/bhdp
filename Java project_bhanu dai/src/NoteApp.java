import javax.swing.*;

public class NoteApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true); // Show the login frame first
        });
    }
}
