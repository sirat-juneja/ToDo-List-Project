package login1;

import java.sql.*;

public class UserDAO {
    public static int validateUser(User user) {
        String query = "SELECT s_no FROM User WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("s_no"); // ✅ return the user ID
            } else {
                return -1; // ❌ login failed
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean registerUser(User user) {
        String query = "INSERT INTO User(username, password) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println(" Username already exists.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}

