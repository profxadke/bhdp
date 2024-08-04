import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private int userId;

    public HomePage(int userId) {
        this.userId = userId;
        setTitle("Home");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton createNoteButton = new JButton("Create Note");
        createNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NoteFrame(userId).setVisible(true);
            }
        });
        panel.add(createNoteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton getNoteButton = new JButton("Read Note");
        getNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new ReadNoteFrame(userId).setVisible(true);
            }
        });
        panel.add(getNoteButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        JButton createTaskButton = new JButton("Create Task");
        createTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaskFrame(userId).setVisible(true);
            }
        });
        panel.add(createTaskButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        JButton readTaskButton = new JButton("Read Task");
        readTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReadTaskFrame(userId).setVisible(true);
            }
        });
        panel.add(readTaskButton, gbc);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage(1).setVisible(true); // Example user ID
        });
    }
}