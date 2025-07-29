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

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    
    // Getters and setters
    public UUID getId() { return id;}
    public void setId(UUID id) {this.id = id;}

    public String getTitle() { return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() { return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDate getDueDate() { return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    public Priority getPriority() { return priority;}
    public void setPriority(Priority priority) {this.priority = priority;}


    public boolean getCompleted() { return completed;}
    public void setCompleted(boolean completed) {this.completed = completed;}

    public AppUser getUser() { return user;}
    public void setUser(AppUser user) {this.user = user;}
}
