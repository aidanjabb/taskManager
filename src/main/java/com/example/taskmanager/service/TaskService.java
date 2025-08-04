package com.example.taskmanager.service;

import java.util.stream.Collectors;
import java.util.UUID;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.AppUser;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;


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
        task.setPriority(request.getPriority());
        task.setCompleted(false);
        task.setUser(user);
        Task savedTask = taskRepo.save(task);

        return TaskResponse.fromEntity(savedTask);
    }

    public List<TaskResponse> getTasksForUser(UUID userId) {
        List<Task> tasks = taskRepo.findByUserId(userId);

        return tasks.stream()
            .map(TaskResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public TaskResponse markTaskAsComplete(UUID taskId) {
        // TODO throws an exception if the task ID doesn’t exist, which returns 500 by default — improve on this error handling later (eg inform the user of the source of the problem, ie this is a task ID that doesn't exist)
        Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new NoSuchElementException("Task not found"));

        task.setCompleted(true);
        Task saved = taskRepo.save(task);

        return TaskResponse.fromEntity(saved);
    }

    public TaskResponse updateTask(UUID taskId, UpdateTaskRequest request) {
        Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new NoSuchElementException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());

        Task saved = taskRepo.save(task);
        return TaskResponse.fromEntity(saved);
    }

    public void deleteTask(UUID taskId) {
        if (!taskRepo.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }
        taskRepo.deleteById(taskId);
    }

}
