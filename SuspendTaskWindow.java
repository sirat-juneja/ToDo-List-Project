package login1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SuspendTaskWindow extends JFrame {

    public SuspendTaskWindow(int userId) {
        setTitle("Suspend Task");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon icon = new ImageIcon("src/login1/icon1.png");
        setIconImage(icon.getImage());

        // Background
        ImageIcon bgIcon = new ImageIcon("src/login1/BlueBg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(null);
        setContentPane(background);

        JLabel idLabel = new JLabel("Enter Task ID:");
        idLabel.setBounds(80, 60, 120, 30);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(200, 60, 200, 30);
        background.add(idField);

        JButton submitBtn = new JButton("Suspend");
        submitBtn.setBounds(200, 140, 100, 35);
        submitBtn.setBackground(new Color(255, 160, 122));
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        background.add(submitBtn);

        submitBtn.addActionListener(e -> {
            String taskIdStr = idField.getText().trim();

            if (taskIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Task ID.");
                return;
            }

            try {
                int taskId = Integer.parseInt(taskIdStr);

                // Retrieve task info before deletion
                String selectQuery = "SELECT task_description, status FROM Task WHERE task_id = ? AND s_no = ?";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {

                    selectStmt.setInt(1, taskId);
                    selectStmt.setInt(2, userId);
                    ResultSet rs = selectStmt.executeQuery();

                    if (rs.next()) {
                        String desc = rs.getString("task_description");
                        String status = rs.getString("status");

                        int confirm = JOptionPane.showConfirmDialog(this,
                                "Task ID: " + taskId + "\nDescription: " + desc + "\nStatus: " + status +
                                        "\n\nAre you sure you want to delete this task?",
                                "Confirm Delete", JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {
                            String deleteQuery = "DELETE FROM Task WHERE task_id = ? AND s_no = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                                deleteStmt.setInt(1, taskId);
                                deleteStmt.setInt(2, userId);
                                deleteStmt.executeUpdate();
                                JOptionPane.showMessageDialog(this, "Task deleted successfully!");
                                idField.setText("");
                                dispose();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Task not found.");
                    }
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid Task ID.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while deleting.");
            }
        });

        setVisible(true);
    }
}
