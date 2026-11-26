/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Main.java
 * Entry point for the application
 * Shows a simple menu to register or login
 */

package com.mycompany.securelogin; // Package for the project

import javax.swing.*; // For Swing components

public class Main {

    public static void main(String[] args) { // Main method
        SwingUtilities.invokeLater(() -> { // Run on the Swing thread
            String[] options = {"Register", "Login", "Exit"}; // Menu options

            while (true) { // Keep showing menu until exit
                int choice = JOptionPane.showOptionDialog( // Show menu dialog
                    null,
                    "Choose an option:",
                    "SecureLogin",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
                );

                if (choice == 0) { // Register chosen
                    new RegisterForm(); // Open register form
                } else if (choice == 1) { // Login chosen
                    new LoginForm(); // Open login form
                } else { // Exit or closed
                    System.exit(0); // Exit the program
                }
            }
        });
    }
}