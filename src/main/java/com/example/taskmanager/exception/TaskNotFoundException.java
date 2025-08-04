package com.example.taskmanager.exception;

// throw this exception as part of our negative testing for the DELETE endpt
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}
