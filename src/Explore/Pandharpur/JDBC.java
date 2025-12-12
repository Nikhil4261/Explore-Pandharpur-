package Explore.Pandharpur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/city";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection connection = null;

    // Singleton Connection Method
    public static Connection connect() {
        if (connection == null) {
            try {
                // Load MySQL JDBC Driver (optional but recommended)
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to the database successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("⚠️ MySQL JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("⚠️ Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    // Close Connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔴 Database connection closed.");
            } catch (SQLException e) {
                System.err.println("⚠️ Failed to close connection: " + e.getMessage());
            }
        }
    }
}
