package com.example.catfoodv1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // --- Authorization Rules ---
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/api/secure/**", "/user/settings/**", "/admin/**").authenticated()
                        .anyRequest().permitAll()
        );

        // --- Form Login ---
        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("accountCode")
                .permitAll()
        );

        // --- Logout ---
        http.logout(LogoutConfigurer::permitAll);

        // --- CSRF Configuration ---
        List<String> protectedPaths = List.of(
                "/api/secure/",
                "/user/settings",
                "/admin/"
        );

        http.csrf(csrf -> csrf.ignoringRequestMatchers(request -> {
            String path = request.getRequestURI();
            // Ignore H2 console
            if (path.startsWith("/h2-console/")) {
                return true;
            }
            // Your original logic: CSRF protection is enabled ONLY for "protected paths".
            // This means we ignore any path that is NOT a protected path.
            boolean isProtected = protectedPaths.stream().anyMatch(path::startsWith);
            return !isProtected;
        }));

        // --- Headers for H2 Console ---
        // Allow H2 console to be embedded in a frame
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
