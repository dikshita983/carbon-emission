package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // --- Read environment variables ---
    private static final String DB_URL = getEnvVar("DB_URL", "jdbc:mysql://localhost:3306/projectcrud?useSSL=false");
    private static final String DB_USER = getEnvVar("DB_USER", "root");
    private static final String DB_PASSWORD = getEnvVar("DB_PASSWORD", "");

    // --- This block runs once when the class is first loaded ---
    static {
        try {
            // --- THIS IS THE FIX ---
            // Manually load the MySQL driver to make sure it's registered.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL ERROR: MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    /**
     * Helper utility to read environment variables with a fallback default value.
     */
    private static String getEnvVar(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    /**
     * --- Centralized connection method ---
     */
    public static Connection getConnection() throws SQLException {
        // This will now find the driver that was loaded in the static block.
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}