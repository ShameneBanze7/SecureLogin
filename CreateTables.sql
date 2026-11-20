-- CreateTables.sql
-- Run this in your database to create the two tables

CREATE TABLE users ( -- Table for storing registered users
    id INT PRIMARY KEY AUTO_INCREMENT, -- Unique ID, auto-increments
    username VARCHAR(50) UNIQUE NOT NULL, -- Username must be unique and not empty
    password_hash VARCHAR(255) NOT NULL, -- Stores the hashed password
    salt VARCHAR(255) NOT NULL, -- Stores the random salt for this user
    failed_attempts INT DEFAULT 0, -- Counts failed login attempts
    locked_until TIMESTAMP NULL -- Time when lockout ends (null if not locked)
);

CREATE TABLE login_audit ( -- Table for recording every login attempt
    id INT PRIMARY KEY AUTO_INCREMENT, -- Unique ID, auto-increments
    username VARCHAR(50), -- Who tried to log in
    attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- When they tried
    success BOOLEAN, -- True if login worked, false if not
    reason VARCHAR(100) -- Why it failed or "ok" if success
);