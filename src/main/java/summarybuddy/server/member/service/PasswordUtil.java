package summarybuddy.server.member.service;

import java.security.SecureRandom;

public class PasswordUtil {
    public static String generateTemporaryPassword() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        SecureRandom random = new SecureRandom();
        StringBuilder tempPassword = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }
}