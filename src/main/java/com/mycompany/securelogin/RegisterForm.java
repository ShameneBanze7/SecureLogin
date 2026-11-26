package com.mycompany.securelogin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * RegisterForm.java
 * Swing form for registering a new user
 * Simple two-field form with a button
 */


import javax.swing.*; // For Swing components
import java.awt.*; // For layout

public class RegisterForm extends JFrame {

    private JTextField usernameField; // Field for username
    private JPasswordField passwordField; // Field for password
    private AuthService authService = new AuthService(); // Service to handle registration

    public RegisterForm() { // Constructor
        setTitle("Register"); // Set window title
        setSize(350, 200); // Set window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)); // Create panel with grid layout
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Add padding

        panel.add(new JLabel("Username:")); // Label for username
        usernameField = new JTextField(); // Create username field
        panel.add(usernameField); // Add to panel

        panel.add(new JLabel("Password:")); // Label for password
        passwordField = new JPasswordField(); // Create password field
        panel.add(passwordField); // Add to panel

        JButton registerBtn = new JButton("Register"); // Create register button
        registerBtn.addActionListener(e -> register()); // Add click handler
        panel.add(registerBtn); // Add to panel

        add(panel); // Add panel to window
        setVisible(true); // Show the window
    }

    private void register() { // Handle register button click
        String username = usernameField.getText().trim(); // Get username
        String password = new String(passwordField.getPassword()); // Get password

        String result = authService.register(username, password); // Call register method

        if (result.equals("OK")) { // If registration succeeded
            JOptionPane.showMessageDialog(this, "Registration successful!"); // Show success
            dispose(); // Close the window
        } else { // If registration failed
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE); // Show error
        }
    }
}