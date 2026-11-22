/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * AuthService.java
 * Contains the registration and login logic
 * This is the brain of the application
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.SQLException; // For database errors
import java.sql.Timestamp; // For timestamps

public class AuthService {

    private UserDAO userDAO = new UserDAO(); // For user database operations
    private AuditDAO auditDAO = new AuditDAO(); // For audit log operations

    private static final int MAX_ATTEMPTS = 5; // Max failed attempts before lockout
    private static final int LOCK_MINUTES = 15; // How long the lockout lasts

    public String register(String username, String password) { // Register a new user
        try {
            if (username == null || username.trim().isEmpty()) { // Check username is not empty
                return "Username cannot be empty"; // Return error
            }

            if (!PasswordUtil.isStrongPassword(password)) { // Check password strength
                return "Password must be at least 8 characters, contain a number and a symbol";
            }

            if (userDAO.usernameExists(username)) { // Check if username is taken
                return "Username already taken"; // Return error
            }

            String salt = PasswordUtil.generateSalt(); // Generate a random salt
            String hash = PasswordUtil.hashPassword(password, salt); // Hash the password
            userDAO.registerUser(username, hash, salt); // Save to database
            return "OK"; // Return success

        } catch (Exception e) { // Catch any error
            return "Error: " + e.getMessage(); // Return error message
        }
    }

    public String login(String username, String password) { // Log in a user
        try {
            User user = userDAO.findByUsername(username); // Find the user

            if (user == null) { // If user doesn't exist
                auditDAO.logAttempt(username, false, "user not found"); // Log the attempt
                return "Invalid username or password"; // Return vague error (don't reveal)
            }

            if (user.isLocked()) { // If account is locked
                auditDAO.logAttempt(username, false, "account locked"); // Log it
                return "Account is locked. Try again later."; // Return message
            }

            String hash = PasswordUtil.hashPassword(password, user.getSalt()); // Hash the typed password

            if (hash.equals(user.getPasswordHash())) { // If hash matches stored hash
                userDAO.resetFailedAttempts(username); // Reset failed count
                auditDAO.logAttempt(username, true, "ok"); // Log success
                return "OK"; // Return success
            } else { // If hash doesn't match
                int newAttempts = user.getFailedAttempts() + 1; // Add 1 to failed count
                userDAO.updateFailedAttempts(username, newAttempts); // Save new count

                if (newAttempts >= MAX_ATTEMPTS) { // If reached max attempts
                    Timestamp lockUntil = new Timestamp( // Set lockout time
                        System.currentTimeMillis() + (LOCK_MINUTES * 60 * 1000L));
                    userDAO.lockAccount(username, lockUntil); // Lock account
                    auditDAO.logAttempt(username, false, "account locked"); // Log lockout
                    return "Account locked for " + LOCK_MINUTES + " minutes."; // Return message
                }

                auditDAO.logAttempt(username, false, "wrong password"); // Log failed attempt
                return "Invalid username or password"; // Return error
            }

        } catch (Exception e) { // Catch any error
            return "Error: " + e.getMessage(); // Return error message
        }
    }
}