package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.AppUser;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import org.springframework.stereotype.Service;


@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public TaskResponse createTask(TaskRequest request) {
        AppUser user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(Task.Priority.valueOf(request.getPriority().toUpperCase()));
        task.setCompleted(false);
        task.setUser(user);
        Task savedTask = taskRepo.save(task);

        return new TaskResponse(savedTask.getId(), savedTask.getTitle(), savedTask.getDescription(), savedTask.getDueDate(), savedTask.getPriority(), savedTask.getCompleted());
    }
}
