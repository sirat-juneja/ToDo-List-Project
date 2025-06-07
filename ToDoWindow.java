package login1;

import javax.swing.*;
import java.awt.*;

public class ToDoWindow extends JFrame {
    int userId;

    public ToDoWindow(String username, int userId) {
        this.userId = userId;
        setTitle("To-Do Dashboard");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ✅ Set window icon
        ImageIcon icon = new ImageIcon("src/login1/icon1.png");
        setIconImage(icon.getImage());

        // ✅ Load and scale background image
        ImageIcon bgIcon = new ImageIcon("src/login1/BlueBg.png ");
                Image scaledImage = bgIcon.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
        ImageIcon scaledBgIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(scaledBgIcon);
        background.setBounds(0, 0, 400, 500);
        background.setLayout(null);
        setContentPane(background);

        // ✅ Title Label
        JLabel title = new JLabel("Welcome, " + username.toUpperCase() + "!", SwingConstants.CENTER);
        title.setBounds(45, 30, 300, 50);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.black); // Optional: Make it visible if background is dark
        background.add(title);


        // ✅ Buttons
        String[] btnLabels = {"Add Task", "Suspend Task", "View Task", "Mark as Done"};
        int yPos = 120;

        for (String label : btnLabels) {
            JButton btn = new JButton(label);
            btn.setBounds(100, yPos, 180, 40);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBackground(new Color(173, 216, 230));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            background.add(btn);
            yPos += 60;

            // (Optional) Dummy click handler
            btn.addActionListener(e -> {
                switch (label) {
                    case "Add Task":
                        new AddTaskWindow(userId);
                        break;
                    case "Suspend Task":
                        new SuspendTaskWindow(userId);
                        break;
                    case "View Task":
                        new ViewTaskWindow(userId, username);
                        break;
                    case "Mark as Done":
                        new MarkAsDoneWindow(userId);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Unknown option");
                }
            });

        }

        setVisible(true);
    }
}
