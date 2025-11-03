package pkguserdao;

public interface UserDao {
    boolean registerUser(String name, String email, String age, String password,  String healthStatus) throws Exception;
    boolean checkEmailExists(String email) throws Exception;
    boolean verifyUsernameEmail(String username, String email);
    boolean updatePasswordByUsername(String username, String newPassword);
    String getHealthByEmail(String email);
    int getAgeByEmail(String email);
    
}