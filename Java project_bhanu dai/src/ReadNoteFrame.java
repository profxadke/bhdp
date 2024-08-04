import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class ReadNoteFrame extends JFrame {
    private int user_id;

    public ReadNoteFrame(int user_id) {
        this.user_id = user_id;
        setTitle("Notes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] notes = readNotesFromDatabase(user_id);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Title: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(new JLabel(notes[0]), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Content: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel(notes[1]), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(formPanel, BorderLayout.CENTER);
    }

    private class Done implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private String[] readNotesFromDatabase(int user_id) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String title = "";
        String content = "";
//        String sql = "SELECT * FROM notes WHERE user_id = 1";
        String sql = "SELECT * FROM notes WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();) {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                title = resultSet.getString("title") ;
                content = resultSet.getString("content");
                System.out.println("Title: " + title + ", Content: " + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
             return null;
        }
        return new String[] {title, content};
    }
}