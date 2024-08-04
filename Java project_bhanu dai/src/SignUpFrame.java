import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SignUpFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignUpFrame() {
        setTitle("Sign Up");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel();
        imagePanel.add(new JLabel(new ImageIcon("path/to/your/image.jpg")));
        add(imagePanel, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Sign Up and Explore"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Create Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Create Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SignUpAction());
        formPanel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JButton loginButton = new JButton("Already have an account? Sign In");
        loginButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private class SignUpAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(SignUpFrame.this, "User registered successfully!");
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(SignUpFrame.this, "Registration failed. Username may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, username);
            p.setString(2, password);
            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUpFrame().setVisible(true);
        });
    }
}