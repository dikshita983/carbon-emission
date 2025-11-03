package utils;

 

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hashes the user's plain text password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify entered password with hashed password from database
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
