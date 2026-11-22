/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * User.java
 * Model class for a user
 * Stores user data from the database
 */

package com.mycompany.securelogin; // Package for the project

import java.sql.Timestamp; // For handling timestamps

public class User {

    private int id; // Unique user ID
    private String username; // Login name
    private String passwordHash; // Hashed password
    private String salt; // Salt used for hashing
    private int failedAttempts; // Number of failed login attempts
    private Timestamp lockedUntil; // Time when lockout ends

    public User(int id, String username, String passwordHash, String salt,
                int failedAttempts, Timestamp lockedUntil) { // Constructor
        this.id = id; // Set ID
        this.username = username; // Set username
        this.passwordHash = passwordHash; // Set password hash
        this.salt = salt; // Set salt
        this.failedAttempts = failedAttempts; // Set failed attempts
        this.lockedUntil = lockedUntil; // Set lockout time
    }

    public int getId() { return id; } // Getter for ID
    public String getUsername() { return username; } // Getter for username
    public String getPasswordHash() { return passwordHash; } // Getter for password hash
    public String getSalt() { return salt; } // Getter for salt
    public int getFailedAttempts() { return failedAttempts; } // Getter for failed attempts
    public Timestamp getLockedUntil() { return lockedUntil; } // Getter for lockout time

    public boolean isLocked() { // Check if account is currently locked
        if (lockedUntil == null) return false; // If no lockout time, not locked
        return lockedUntil.after(new Timestamp(System.currentTimeMillis())); // Locked if lockout time is in the future
    }
}