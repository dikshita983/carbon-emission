package pkguserdao;

import java.sql.*;

import utils.DBUtil;
import utils.PasswordUtil;

public class Logindao {
    public boolean checkUserExists(String email) {
        try {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("select * from registration where email = ?")) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                boolean exists = rs.next();
                
                if (exists) {
                    
                    String password = rs.getString("password");
                    saveToLoginTable(email, password, con);
                }
                return exists;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveToLoginTable(String email, String hashedPassword, Connection con) {
        try {
            
            String deleteSql = "DELETE FROM login WHERE email = ?";
            try (PreparedStatement deletePs = con.prepareStatement(deleteSql)) {
                deletePs.setString(1, email);
                deletePs.executeUpdate();
            }

            String insertSql = "INSERT INTO login (email, password) VALUES (?, ?)";
            try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                insertPs.setString(1, email);
                insertPs.setString(2, hashedPassword); // Use the hashed password directly
                int result = insertPs.executeUpdate();
                System.out.println("Login entry created/updated for: " + email + ", Result: " + result);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean validate(String email, String plainPassword) {
        try {
           
            try (Connection con = DBUtil.getConnection()){
            		
                PreparedStatement psReg = con.prepareStatement("SELECT password FROM registration WHERE email = ?");
                psReg.setString(1, email);
                ResultSet rsReg = psReg.executeQuery();
                
                if (rsReg.next()) {
                    // Get the hashed password stored in registration table
                    String storedHashedPassword = rsReg.getString("password");
                    
                    // Verify the password using BCrypt
                    if (PasswordUtil.checkPassword(plainPassword, storedHashedPassword)) {
                        // Passwords match - allow login
                        saveToLoginTable(email, storedHashedPassword, con);
                        return true;
                    } else {
                        // Passwords don't match
                        System.out.println("Password mismatch for: " + email);
                        return false;
                    }
                }
                // Email not found in registration
                System.out.println("User not found: " + email);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Validation error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String[] getUserDetails(String email) {
        try {
            
        	String sql = "SELECT name, age, healthcondition FROM registration WHERE email = ?";
            try (
            	 Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    String[] userDetails = new String[3];
                    userDetails[0] = rs.getString("name");
                    userDetails[1] = rs.getString("age");
                    userDetails[2] = rs.getString("healthcondition");
                    return userDetails;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}