import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NoteFrame extends JFrame {
    private int userId;
    private JTextField titleField;
    private JTextArea contentArea;

    public NoteFrame(int userId) {
        this.userId = userId;
        setTitle("Create Note");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Content:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentArea = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(contentArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveNoteAction());
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private class SaveNoteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String content = contentArea.getText();

            if (saveNoteToDatabase(title, content)) {
                JOptionPane.showMessageDialog(NoteFrame.this, "Note saved successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(NoteFrame.this, "Failed to save note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean saveNoteToDatabase(String title, String content) {
        String sql = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, userId);
            p.setString(2, title);
            p.setString(3, content);
            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}