package login1;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTaskWindow extends JFrame {

    public AddTaskWindow(int userId) {
        setTitle("Add New Task");
        setSize(500, 300); // Landscape
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window

        // Set icon (optional)
        ImageIcon icon = new ImageIcon("src/login1/icon1.png");
        setIconImage(icon.getImage());

        // Background image scaled to fit
        ImageIcon bgIcon = new ImageIcon("src/login1/BlueBg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setBounds(0, 0, 400, 300);
        background.setLayout(null);
        setContentPane(background);

        // Label
        JLabel taskLabel = new JLabel("Enter Task:");
        taskLabel.setBounds(80, 60, 100, 30);
        taskLabel.setFont(new Font("Arial", Font.BOLD, 16));
        taskLabel.setForeground(Color.black);
        background.add(taskLabel);

        // TextField
        JTextField taskField = new JTextField();
        taskField.setBounds(200, 60, 200, 30);
        background.add(taskField);

        // Submit Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(200, 140, 100, 35);
        submitBtn.setBackground(new Color(173, 216, 230));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        background.add(submitBtn);

        // Submit Action
        submitBtn.addActionListener(e -> {
            String taskDesc = taskField.getText().trim();

            if (taskDesc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a task.");
                return;
            }

            // Insert into DB
            String query = "INSERT INTO Task(s_no, task_description) VALUES (?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setString(2, taskDesc);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Task added successfully!");
                taskField.setText("");
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add task.");
            }
        });

        setVisible(true);
    }
}
