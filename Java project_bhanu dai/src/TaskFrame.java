import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class TaskFrame extends JFrame {
    private int user_id;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JSpinner dueDateSpinner;

    public TaskFrame(int user_id) {
        this.user_id = user_id;
        setTitle("Create Task");
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
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        descriptionArea = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Due Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        dueDateSpinner = new JSpinner(new SpinnerDateModel());
        formPanel.add(dueDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveTaskAction());
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private class SaveTaskAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            java.util.Date dueDate = (java.util.Date) dueDateSpinner.getValue();

            if (saveTaskToDatabase(title, description, new Date(dueDate.getTime()))) {
                JOptionPane.showMessageDialog(TaskFrame.this, "Task saved successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(TaskFrame.this, "Failed to save task.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean saveTaskToDatabase(String title, String description, Date dueDate) {
//        String.out.println("title: " + title);
        String sql = "INSERT INTO tasks (user_id, title, description, due_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, user_id);
            p.setString(2, title);
            p.setString(3, description);
            p.setDate(4, dueDate);
            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}