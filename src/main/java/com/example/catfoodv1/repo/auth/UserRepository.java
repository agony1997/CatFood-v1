package com.example.catfoodv1.repo.auth;

import com.example.catfoodv1.model.entity.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountCode(String accountCode);
}
