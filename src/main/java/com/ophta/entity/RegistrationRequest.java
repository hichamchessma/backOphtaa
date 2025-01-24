package com.ophta.entity;

import lombok.Data;

import java.util.Set;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private Set<String> roles;; // For role-based access
    private String email;

    // Getters and Setters
}

