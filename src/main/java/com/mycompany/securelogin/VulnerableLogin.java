/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * VulnerableLogin.java
 * DELIBERATELY INSECURE - for demonstration only
 * Shows how string concatenation allows SQL injection
 * NEVER use this in real code
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.*; // For SQL classes

public class VulnerableLogin {

    public boolean login(String username, String password) throws SQLException { // UNSAFE login method
        // WARNING: This is intentionally vulnerable to SQL injection
        String query = "SELECT * FROM users WHERE username = '" + username + "'"; // UNSAFE - joins strings directly

        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             Statement stmt = conn.createStatement()) { // Create plain statement

            ResultSet rs = stmt.executeQuery(query); // Run the unsafe query
            return rs.next(); // Return true if any row found
        }
    }

    public static void main(String[] args) throws SQLException { // Demo method
        VulnerableLogin vl = new VulnerableLogin(); // Create instance

        System.out.println("=== VULNERABLE DEMO - FOR LEARNING ONLY ==="); // Warning message
        System.out.println("Trying normal login..."); // Status message
        boolean normal = vl.login("shamene", "anything"); // Try a normal username
        System.out.println("Normal result: " + normal); // Print result

        System.out.println("\nTrying SQL injection attack..."); // Status message
        boolean attack = vl.login("' OR '1'='1", "anything"); // Try the injection
        System.out.println("Attack result: " + attack); // Should return true - attack worked
        System.out.println("\nIf attack result is true, the system is vulnerable."); // Explain result
    }
}