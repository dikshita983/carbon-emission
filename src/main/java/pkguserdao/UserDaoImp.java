package pkguserdao;

import java.sql.*;

import utils.DBUtil;
import utils.PasswordUtil;
import org.mindrot.jbcrypt.BCrypt; 
public class UserDaoImp implements UserDao {
    private static final String saveData = "insert into registration (name,email,age,password,healthcondition) values(?,?,?,?,?)";
    private static final String checkEmail = "SELECT COUNT(*) FROM registration WHERE email = ?";

    @Override
    public boolean registerUser(String name, String email, String age, String password,  String healthStatus) throws Exception {
        
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(saveData)) {
            
            ps.setString(1, name.trim());
            ps.setString(2, email.trim());
            ps.setString(3, age != null ? age.trim() : "");
            try {
                String hashedPassword = PasswordUtil.hashPassword(password);
                System.out.println("Hashed password length: " + hashedPassword.length());
                ps.setString(4, hashedPassword);
            } catch (Exception e) {
                System.out.println("Error hashing password: " + e.getMessage());
                throw e;
            }
            ps.setString(5, healthStatus != null ? healthStatus.trim() : "");
            
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean checkEmailExists(String email) throws Exception {
                
        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(checkEmail)) {
            
            ps.setString(1, email.trim());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
    public boolean verifyUsernameEmail(String username, String email) {
        String sql = "SELECT * FROM  registration WHERE name=? AND email=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePasswordByUsername(String username, String newPassword) {
        String sql = "UPDATE registration SET password=? WHERE name=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            stmt.setString(1, hashedPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public String getHealthByEmail(String email) {
    	
    	String health = null;
        String sql = "SELECT healthcondition FROM registration WHERE email = ?";
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                health = rs.getString("healthcondition");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return health != null ? health : "none";
    }
     

    @Override
    public int getAgeByEmail(String email) {
        int age = 0;
        String sql = "SELECT age FROM registration WHERE email = ?";
        try (
        		Connection conn = DBUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                age = rs.getInt("age");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }


}