/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * PasswordUtil.java
 * Handles salt generation and password hashing
 * Never store passwords as plain text - always hash them
 */

package com.mycompany.securelogin; // Package for the project

import java.security.SecureRandom; // For generating random salt
import java.security.spec.KeySpec; // For key specification
import java.util.Base64; // For encoding bytes to strings
import javax.crypto.SecretKeyFactory; // For creating the hash
import javax.crypto.spec.PBEKeySpec; // For password-based encryption

public class PasswordUtil {

    public static String generateSalt() { // Method to generate a random salt
        byte[] salt = new byte[16]; // Create an array of 16 bytes
        new SecureRandom().nextBytes(salt); // Fill it with random bytes
        return Base64.getEncoder().encodeToString(salt); // Convert to a readable string
    }

    public static String hashPassword(String password, String salt) throws Exception { // Method to hash a password
        KeySpec spec = new PBEKeySpec( // Create a key specification
            password.toCharArray(), // Convert password to characters
            Base64.getDecoder().decode(salt), // Decode the salt back to bytes
            65536, // Number of times to repeat the hash (makes it slow for attackers)
            256); // Length of the hash in bits

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); // Get the hashing algorithm
        byte[] hash = factory.generateSecret(spec).getEncoded(); // Generate the hash
        return Base64.getEncoder().encodeToString(hash); // Convert hash to a readable string
    }

    public static boolean isStrongPassword(String password) { // Method to check password strength
        if (password.length() < 8) return false; // Must be at least 8 characters
        boolean hasNumber = password.matches(".*\\d.*"); // Must contain a number
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Must contain a symbol
        return hasNumber && hasSymbol; // Return true only if both are present
    }
}
