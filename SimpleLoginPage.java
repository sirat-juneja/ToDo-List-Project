package login1;

import javax.swing.*;
import java.awt.*;

public class SimpleLoginPage {

    public static void main(String[] args) {
        new SimpleLoginPage().createLoginUI();
    }

    public void createLoginUI() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        ImageIcon image = new ImageIcon("src/login1/icon1.png");
        frame.setIconImage(image.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(161, 213, 246));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Log in");
        loginButton.setBounds(55, 100, 80, 25);
        panel.add(loginButton);

        JButton signupButton = new JButton("Sign up");
        signupButton.setBounds(150, 100, 80, 25);
        panel.add(signupButton);

        // ✅ LOGIN logic
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username and password.");
                return;
            }

            User user = new User(username, password);
            int userId = UserDAO.validateUser(user);
            if (userId != -1) {
                JOptionPane.showMessageDialog(frame, "✅ Login successful!");
                frame.dispose(); // close login window
                new ToDoWindow(user.getUsername(), userId); //  pass username and userId
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Invalid username or password.");
            }

        });

        // ✅ SIGNUP logic
        signupButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter username and password.");
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(frame, "⚠️ Password must be at least 8 characters.");
                return;
            }

            User user = new User(username, password);
            if (UserDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(frame, "✅ Signup successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Signup failed.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
