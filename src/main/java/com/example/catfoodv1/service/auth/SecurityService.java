package com.example.catfoodv1.service.auth;

import com.example.catfoodv1.aspect.NoLogging;
import com.example.catfoodv1.model.dto.UserDto;
import com.example.catfoodv1.model.entity.auth.Account;
import com.example.catfoodv1.model.entity.auth.Role;
import com.example.catfoodv1.repo.auth.AccountRepository;
import com.vaadin.flow.component.UI;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityService {

    private final AccountRepository accountRepository;

    public SecurityService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
    public Optional<String> getLoginUserName() {
        return getAuthenticatedUserEntity().map(Account::getDisplayName);
    }

    /**
     * 獲取當前登入使用者的角色名稱集合。
     * @return 返回一個包含角色名稱的 Set。如果使用者未登入，則返回一個空集合。
     */
    public Set<String> getLoginRoles() {
        return getAuthenticatedUserEntity()
                .map(user -> user.getRoles().stream()
                        .map(Role::getRoleCode)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

    /**
     * 獲取當前登入的使用者實體（內部使用）。
     * @return 如果使用者已登入，則返回包含 User 的 Optional；否則返回空的 Optional。
     */
    @NoLogging
    public Optional<Account> getAuthenticatedUserEntity() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String accountCode = ((UserDetails) principal).getUsername();
            return accountRepository.findByAccountCode(accountCode);
        }

        return Optional.empty();
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation("/logout");
    }
}
