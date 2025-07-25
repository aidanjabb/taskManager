package com.example.taskmanager.model;

import java.util.UUID;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    // Getters, setters, constructors
}
