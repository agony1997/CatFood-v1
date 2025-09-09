package com.example.catfoodv1.config;

import com.example.catfoodv1.model.entity.auth.Account;
import com.example.catfoodv1.model.entity.auth.Role;
import com.example.catfoodv1.repo.auth.RoleRepository;
import com.example.catfoodv1.repo.auth.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
//@Profile("h2")
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 建立角色
        Role userRole = createRoleIfNotFound("ROLE_USER", "一般使用者");
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "管理員");
        // 建立管理員帳號
        createUserIfNotFound("admin", "admin@example.com", "123", "Agony", Set.of(adminRole, userRole));
        createUserIfNotFound("normal", "normal@example.com", "123", "User", Set.of(userRole));
    }

    private Role createRoleIfNotFound(String code, String name) {
        return roleRepository.findByRoleCode(code).orElseGet(() -> roleRepository.save(new Role(code, name)));
    }

    private void createUserIfNotFound(String accountCode, String email, String password, String username, Set<Role> roles) {
        if (accountRepository.findByAccountCode(accountCode).isEmpty()) {
            Account user = new Account();
            user.setAccountCode(accountCode);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password)); // 加密密碼
            user.setDisplayName(username);
            user.setRoles(roles);
            accountRepository.save(user);
        }
    }
}
