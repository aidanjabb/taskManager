package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.example.taskmanager.repository.UserRepository;

// TODO add some comment abt what we are actually doing here, related to what the @SpringBootTest annotation actually does

@TestPropertySource(properties = {
  "spring.datasource.url=jdbc:postgresql://db:5432/taskdb",
  "spring.datasource.username=postgres",
  "spring.datasource.password=postgres"
})
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
