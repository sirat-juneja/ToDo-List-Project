package login1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewTaskWindow extends JFrame {

    public ViewTaskWindow(int userId, String username) {
        setTitle("Your Tasks");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set icon
        ImageIcon icon = new ImageIcon("src/login1/icon1.png");
        setIconImage(icon.getImage());

        // Background
        ImageIcon bgIcon = new ImageIcon("src/login1/BlueBg.png");
        Image scaledImage = bgIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        background.setLayout(null);
        setContentPane(background);

        // Personalized label
        JLabel welcomeLabel = new JLabel(username.toUpperCase() + ", your tasks are:", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(20, 20, 400, 30);
        background.add(welcomeLabel);

        // Table setup
        String[] columnNames = {"ID", "Description", "Status", "Created At"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 70, 550, 250);
        background.add(scrollPane);

        // Set custom column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(250); // Description
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Status
        table.getColumnModel().getColumn(3).setPreferredWidth(190); // Created At

        // Fetch tasks from DB
        String query = "SELECT task_id, task_description, status, created_at FROM Task WHERE s_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("task_id");
                String desc = rs.getString("task_description");
                String status = rs.getString("status");
                String createdAt = rs.getString("created_at");
                model.addRow(new Object[]{id, desc, status, createdAt});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to fetch tasks.");
        }

        setVisible(true);
    }
}
