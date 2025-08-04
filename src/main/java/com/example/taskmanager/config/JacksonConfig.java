package com.example.taskmanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
got an error indicating that Jackson (the JSON processor used by Spring Boot) doesn’t know how to serialize or deserialize java.time.LocalDate by default — until you register a specific module.

In Spring Boot 2.6+ or 3.x, Spring should auto-detect the module.
But if it doesn’t, add a configuration bean manually

*/
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
