package com.example.catfoodv1;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(
                                        "/login", "/logout",
                                        "/VAADIN/**", "/frontend/**", "/webjars/**",
                                        "/images/**", "/icons/**", "/manifest.webmanifest",
                                        "/sw.js", "/offline-page.html", "/VAADIN/build/**"
                                ).permitAll()
                                .anyRequest().authenticated())
                .formLogin(form -> form
//                        .loginPage("/login") // 指定自訂登入頁面，若無自訂則可刪除此行，使用預設登入頁
                                .permitAll()         // 登入頁允許匿名訪問
                )
                .logout(LogoutConfigurer::permitAll);    // 登出功能允許匿名訪問

        List<String> protectedPaths = List.of(
                "/api/secure/",
                "/user/settings",
                "/admin/"
        );

        http.csrf(csrf ->
                csrf.ignoringRequestMatchers("/**") // 預設略過全部
                        .requireCsrfProtectionMatcher(request -> {
                            String path = request.getRequestURI(); // 每一個經過的request
                            return protectedPaths.stream().anyMatch(path::startsWith); // 目前的 path 是否以清單中任何一個字串為開頭
                        })
        );

        return http.build();
    }
}
