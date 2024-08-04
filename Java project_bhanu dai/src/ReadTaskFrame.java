import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Statement;


public class ReadTaskFrame extends JFrame {
    private int id;

    public ReadTaskFrame(int id) {
        this.id = id;
        setTitle("Notes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // String[] notes = readNotesFromDatabase(user_id);
        // String[] tasks = readTasksFromDatabase(user_id);
        ArrayList<Map<String, String>> tasks = readTasksFromDatabase(id);
        int i = 0; int y = 0;
        for (Map<String, String> task : tasks) {
            gbc.gridx = y;
            gbc.gridy = i;
            formPanel.add(new JLabel("Title: " + task.get("title")), gbc);
            i += 1;

            gbc.gridx = y;
            gbc.gridy = i;
            formPanel.add(new JLabel("Description: " + task.get("description")), gbc);
            i += 1;

            gbc.gridx = y;
            gbc.gridy = i;
            formPanel.add(new JLabel("Due Date: " + task.get("due_date")), gbc);
            i += 1;

            gbc.gridx = y;
            gbc.gridy = i;
            gbc.gridwidth = 2;
            JButton submitButton = new JButton("Delete");
            submitButton.addActionListener(new DeleteAction(Integer.parseInt(task.get("id"))));
            formPanel.add(submitButton, gbc);
            i += 1;

            gbc.gridx = y;
            gbc.gridy = i;
            formPanel.add(new JLabel(" "), gbc);
            i += 1; // y += 1;

            add(formPanel, BorderLayout.CENTER);
        }
    }

    private class Done implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class DeleteAction implements ActionListener {
        private int id;

        public DeleteAction(int id) {
            this.id = id;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println("Task ID: " + id);
            try (Connection conn = DatabaseConnection.getConnection()) {
                Statement query = conn.createStatement();
                String sql = "DELETE FROM tasks WHERE id = " + id + ';';
                int affectedRows = query.executeUpdate(sql);
                System.out.println("Affected rows: " + affectedRows);
                if (affectedRows > 0) {
                    System.out.println("Task deleted successfully.");
                    JOptionPane.showMessageDialog(ReadTaskFrame.this, "Deleted successfully!");
                } else {
                    System.out.println("No task found with the specified ID.");
                    JOptionPane.showMessageDialog(ReadTaskFrame.this, "Unknown error while deleting!");
//                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private ArrayList<Map<String, String>> readTasksFromDatabase(int user_id) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String title = "";
        String description = "";
        ArrayList<Map<String, String>> data = new ArrayList<>();
        java.sql.Date dueDate = null;
//        String sql = "SELECT * FROM notes WHERE user_id = 1";
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();) {
//             PreparedStatement p = conn.prepareStatement(sql)) {
//            p.setInt(1, userId);
//            p.setString(2, title);
//            p.setString(3, content);
//            int rowsAffected = p.executeUpdate();
//            return rowsAffected > 0;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
//            data = preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                title = resultSet.getString("title") ;
                description = resultSet.getString("description");
                dueDate = resultSet.getDate("due_date");
                id = resultSet.getInt("id");
                Map<String, String> payload = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dueDateString = dateFormat.format(dueDate);
                payload.put("title", title);
                payload.put("description", description);
                payload.put("due_date", dueDateString);
                payload.put("id", String.valueOf(id));
//                System.out.println(resultSet);
                data.add(payload);
                // System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
}