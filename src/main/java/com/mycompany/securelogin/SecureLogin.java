/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * SecureLogin.java
 * SECURE version using prepared statements
 * Shows the fix for SQL injection
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.*; // For SQL classes

public class SecureLogin {

    public boolean login(String username, String password) throws SQLException { // SAFE login method
        String query = "SELECT * FROM users WHERE username = ?"; // SAFE - uses placeholder

        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             PreparedStatement ps = conn.prepareStatement(query)) { // Prepare the statement
            ps.setString(1, username); // Set the username parameter - treated as plain text
            ResultSet rs = ps.executeQuery(); // Run the query
            return rs.next(); // Return true if user found
        }
    }

    public static void main(String[] args) throws SQLException { // Demo method
        SecureLogin sl = new SecureLogin(); // Create instance

        System.out.println("=== SECURE DEMO - SHOWS THE FIX ==="); // Status message
        System.out.println("Trying normal login..."); // Status message
        boolean normal = sl.login("shamene", "anything"); // Try a normal username
        System.out.println("Normal result: " + normal); // Print result

        System.out.println("\nTrying SQL injection attack..."); // Status message
        boolean attack = sl.login("' OR '1'='1", "anything"); // Try the injection
        System.out.println("Attack result: " + attack); // Should return false - attack fails
        System.out.println("\nIf attack result is false, the system is safe."); // Explain result
    }
}