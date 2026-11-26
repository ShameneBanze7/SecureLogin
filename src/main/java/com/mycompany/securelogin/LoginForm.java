/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * LoginForm.java
 * Swing form for logging in
 * Takes username and password and shows result
 */

package com.mycompany.securelogin; // Package for the project

import javax.swing.*; // For Swing components
import java.awt.*; // For layout

public class LoginForm extends JFrame {

    private JTextField usernameField; // Field for username
    private JPasswordField passwordField; // Field for password
    private AuthService authService = new AuthService(); // Service to handle login

    public LoginForm() { // Constructor
        setTitle("Login"); // Set window title
        setSize(350, 200); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit when closed
        setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)); // Create panel with grid layout
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Add padding

        panel.add(new JLabel("Username:")); // Label for username
        usernameField = new JTextField(); // Create username field
        panel.add(usernameField); // Add to panel

        panel.add(new JLabel("Password:")); // Label for password
        passwordField = new JPasswordField(); // Create password field
        panel.add(passwordField); // Add to panel

        JButton loginBtn = new JButton("Login"); // Create login button
        loginBtn.addActionListener(e -> login()); // Add click handler
        panel.add(loginBtn); // Add to panel

        add(panel); // Add panel to window
        setVisible(true); // Show the window
    }

    private void login() { // Handle login button click
        String username = usernameField.getText().trim(); // Get username
        String password = new String(passwordField.getPassword()); // Get password

        String result = authService.login(username, password); // Call login method

        if (result.equals("OK")) { // If login succeeded
            JOptionPane.showMessageDialog(this, "Login successful!"); // Show success
        } else { // If login failed
            JOptionPane.showMessageDialog(this, result, "Login Failed", JOptionPane.ERROR_MESSAGE); // Show error
        }
    }
}