package com.example.taskmanager.model;

import java.util.UUID;
import jakarta.persistence.*;


@Entity
public class AppUser {
    @Id // primary key
    @GeneratedValue // ID is generated when entity is saved to DB
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    
    // Getters and setters
    public UUID getId() { return id;}
    public void setId(UUID id) {this.id = id;}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
