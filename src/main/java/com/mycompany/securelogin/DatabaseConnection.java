package com.mycompany.securelogin; // Package for the project

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * DatabaseConnection.java
 * Handles the connection to the database
 * This is the foundation - everything else uses this to talk to the database
 */

import java.sql.Connection; // For database connection
import java.sql.DriverManager; // For managing the driver
import java.sql.SQLException; // For handling database errors

public class DatabaseConnection {

    private static final String URL = "jdbc:derby://localhost:1527/SecureLoginDB"; // Database URL
    private static final String USER = "app"; // Database username (default for Java DB)
    private static final String PASSWORD = "app"; // Database password (default for Java DB)

    public static Connection getConnection() throws SQLException { // Method to get a connection
        return DriverManager.getConnection(URL, USER, PASSWORD); // Return a connection to the database
    }

    public static void main(String[] args) { // Test method to check connection works
        try (Connection conn = getConnection()) { // Try to open a connection
            if (conn != null) { // Check if connection was successful
                System.out.println("Connected to database successfully!"); // Print success message
            }
        } catch (SQLException e) { // Catch any database errors
            System.out.println("Connection failed: " + e.getMessage()); // Print error message
        }
    }
}