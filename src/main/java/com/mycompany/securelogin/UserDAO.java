/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * UserDAO.java
 * Data Access Object for user operations
 * Handles all database queries for users
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.*; // For all SQL classes
import java.sql.Timestamp; // For timestamps

public class UserDAO {

    public boolean usernameExists(String username) throws SQLException { // Check if username is taken
        String query = "SELECT id FROM users WHERE username = ?"; // Query with parameter
        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             PreparedStatement ps = conn.prepareStatement(query)) { // Prepare statement
            ps.setString(1, username); // Set the username parameter
            ResultSet rs = ps.executeQuery(); // Run the query
            return rs.next(); // Return true if a row was found
        }
    }

    public void registerUser(String username, String passwordHash, String salt) throws SQLException { // Register a new user
        String query = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)"; // Insert query
        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             PreparedStatement ps = conn.prepareStatement(query)) { // Prepare statement
            ps.setString(1, username); // Set username
            ps.setString(2, passwordHash); // Set password hash
            ps.setString(3, salt); // Set salt
            ps.executeUpdate(); // Run the insert
        }
    }

    public User findByUsername(String username) throws SQLException { // Find a user by username
        String query = "SELECT * FROM users WHERE username = ?"; // Select query
        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             PreparedStatement ps = conn.prepareStatement(query)) { // Prepare statement
            ps.setString(1, username); // Set username
            ResultSet rs = ps.executeQuery(); // Run query
            if (rs.next()) { // If user found
                return new User( // Create and return a User object
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("salt"),
                    rs.getInt("failed_attempts"),
                    rs.getTimestamp("locked_until")
                );
            }
        }
        return null; // Return null if not found
    }

    public void updateFailedAttempts(String username, int attempts) throws SQLException { // Update failed attempts count
        String query = "UPDATE users SET failed_attempts = ? WHERE username = ?"; // Update query
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, attempts); // Set new count
            ps.setString(2, username); // Set username
            ps.executeUpdate(); // Run update
        }
    }

    public void lockAccount(String username, Timestamp lockedUntil) throws SQLException { // Lock an account
        String query = "UPDATE users SET locked_until = ? WHERE username = ?"; // Update query
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setTimestamp(1, lockedUntil); // Set lockout time
            ps.setString(2, username); // Set username
            ps.executeUpdate(); // Run update
        }
    }

    public void resetFailedAttempts(String username) throws SQLException { // Reset failed attempts on success
        String query = "UPDATE users SET failed_attempts = 0, locked_until = NULL WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username); // Set username
            ps.executeUpdate(); // Run update
        }
    }
}