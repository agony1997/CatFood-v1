package com.example.catfoodv1.service.auth;

import com.example.catfoodv1.dto.UserDto;
import com.example.catfoodv1.model.entity.auth.Role;
import com.example.catfoodv1.model.entity.auth.User;
import com.example.catfoodv1.repo.auth.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 獲取當前登入的使用者DTO。
     * @return 如果使用者已登入，則返回包含 UserDto 的 Optional；否則返回空的 Optional。
     */
    public Optional<UserDto> getLoginUserDto() {
        return getAuthenticatedUserEntity().map(UserDto::fromEntity);
    }

    /**
     * 獲取當前登入使用者的顯示名稱 (Username)。
     * @return 如果使用者已登入，則返回其顯示名稱；否則返回空的 Optional。
     */
    public Optional<String> getLoginUsername() {
        return getAuthenticatedUserEntity().map(User::getUsername);
    }

    /**
     * 獲取當前登入使用者的角色名稱集合。
     * @return 返回一個包含角色名稱的 Set。如果使用者未登入，則返回一個空集合。
     */
    public Set<String> getLoginRoles() {
        return getAuthenticatedUserEntity()
                .map(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    /**
     * 獲取當前登入的使用者實體（內部使用）。
     * @return 如果使用者已登入，則返回包含 User 的 Optional；否則返回空的 Optional。
     */
    public Optional<User> getAuthenticatedUserEntity() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(email);
        }

        return Optional.empty();
    }
}
