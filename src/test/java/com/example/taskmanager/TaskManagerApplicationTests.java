package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.taskmanager.repository.UserRepository;


@ActiveProfiles("test")
@SpringBootTest
class TaskManagerApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        assert userRepository != null;
    }
}
