package com.example.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    /* disable HTTP Basic Auth (enforced by spring-boot-starter-security dependency) since we haven't implemented login or security yet
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                          // optional
                                "/v3/api-docs",               // exact
                                "/v3/api-docs/**",            // nested (e.g., /v3/api-docs/swagger-config)
                                "/v3/api-docs.yaml",          // yaml variant
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html"      // some setups hit this directly
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // allow anonymous users to be treated as a principal (prevents 403s when no auth is provided)
                .anonymous(Customizer.withDefaults())
                // keep httpBasic enabled (or remove if you truly don't want it; docs are already permitted)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
