package com.example.catfood.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/secure/**", "/user/settings/**", "/admin/**").authenticated()
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("accountCode")
                .permitAll()
        );

        http.logout(LogoutConfigurer::permitAll);

        List<String> protectedPaths = List.of("/api/secure/", "/user/settings", "/admin/");
        http.csrf(csrf -> csrf.ignoringRequestMatchers(request -> {
            String path = request.getRequestURI();
            boolean isProtected = protectedPaths.stream().anyMatch(path::startsWith);
            return !isProtected;
        }));

        return http.build();
    }
}
