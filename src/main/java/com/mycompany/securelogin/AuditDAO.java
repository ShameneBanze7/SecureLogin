/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * AuditDAO.java
 * Records every login attempt in the audit log
 * This is the "guard's notebook" - feeds Project 2 later
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.*; // For SQL classes

public class AuditDAO {

    public void logAttempt(String username, boolean success, String reason) throws SQLException { // Log one attempt
        String query = "INSERT INTO login_audit (username, success, reason) VALUES (?, ?, ?)"; // Insert query
        try (Connection conn = DatabaseConnection.getConnection(); // Open connection
             PreparedStatement ps = conn.prepareStatement(query)) { // Prepare statement
            ps.setString(1, username); // Set username
            ps.setBoolean(2, success); // Set success true or false
            ps.setString(3, reason); // Set reason text
            ps.executeUpdate(); // Run insert
        }
    }
}