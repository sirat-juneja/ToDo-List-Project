package login1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MarkAsDoneWindow extends JFrame {

    public MarkAsDoneWindow(int userId) {
        setTitle("Mark Task as Completed");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set window icon
        ImageIcon icon = new ImageIcon("src/login1/icon1.png");
        setIconImage(icon.getImage());

        // Background image
        ImageIcon bgIcon = new ImageIcon("src/login1/BlueBg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(null);
        setContentPane(background);

        // Label
        JLabel idLabel = new JLabel("Enter Task ID:");
        idLabel.setBounds(80, 60, 120, 30);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(idLabel);

        // TextField
        JTextField idField = new JTextField();
        idField.setBounds(200, 60, 200, 30);
        background.add(idField);

        // Button
        JButton submitBtn = new JButton("Mark as Done");
        submitBtn.setBounds(180, 140, 140, 35);
        submitBtn.setBackground(new Color(144, 238, 144));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        background.add(submitBtn);

        // Action listener
        submitBtn.addActionListener(e -> {
            String taskIdStr = idField.getText().trim();

            if (taskIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Task ID.");
                return;
            }

            try {
                int taskId = Integer.parseInt(taskIdStr);

                String selectQuery = "SELECT task_description, status FROM Task WHERE task_id = ? AND s_no = ?";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

                    selectStmt.setInt(1, taskId);
                    selectStmt.setInt(2, userId);
                    ResultSet rs = selectStmt.executeQuery();

                    if (rs.next()) {
                        String desc = rs.getString("task_description");
                        String status = rs.getString("status");

                        if ("Completed".equalsIgnoreCase(status)) {
                            JOptionPane.showMessageDialog(this, "⚠️ Task is already marked as completed.");
                            return;
                        }

                        int confirm = JOptionPane.showConfirmDialog(this,
                                "Task ID: " + taskId + "\nDescription: " + desc + "\nCurrent Status: " + status +
                                        "\n\nMark this task as completed?",
                                "Confirm Mark as Done", JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {
                            String updateQuery = "UPDATE Task SET status = 'Completed' WHERE task_id = ? AND s_no = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setInt(1, taskId);
                                updateStmt.setInt(2, userId);
                                updateStmt.executeUpdate();
                                JOptionPane.showMessageDialog(this, "✅ Task marked as completed.");
                                dispose(); // Close window
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Task not found.");
                    }
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "❌ Invalid Task ID.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "⚠️ Failed to mark task as completed.");
            }
        });

        setVisible(true);
    }
}
