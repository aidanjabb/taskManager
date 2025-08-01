package com.example.taskmanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Task.Priority;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)  // 🚨 disables security filters
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetTasksByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        TaskResponse sampleTask = new TaskResponse(
                UUID.randomUUID(),
                "Test task",
                "testing",
                LocalDate.now(),
                Priority.MEDIUM,
                false
        );

        when(taskService.getTasksForUser(userId))
                .thenReturn(Collections.singletonList(sampleTask));

        /*
        positive testing; given valid input, check for HTTP 200 OK and correct JSON structure being returned
        */
        mockMvc.perform(get("/tasks")
                        .param("userId", userId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].priority").value("MEDIUM"));
    }


    @Test
    void testCreateTask_Success() throws Exception {
        UUID taskId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        TaskRequest request = new TaskRequest(
            userId,
            "Buy groceries",
            "Get milk and eggs",
            LocalDate.of(2025, 8, 1),
            Priority.HIGH
        );

        TaskResponse response = new TaskResponse(
            taskId,
            request.getTitle(),
            request.getDescription(),
            request.getDueDate(),
            request.getPriority(),
            false // completed
        );

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(response);

        /*
        positive testing; given valid input, check for HTTP 201 Created and correct JSON structure being returned
        */
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(taskId.toString()))
            .andExpect(jsonPath("$.title").value("Buy groceries"))
            .andExpect(jsonPath("$.priority").value("HIGH"))
            .andExpect(jsonPath("$.completed").value(false));
    }
}

