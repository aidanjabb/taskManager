package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

// same as AppUser, except no id, since that is generated when entity is saved to DB
public class UserRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String email;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
