package com.example.catfoodv1.repo.auth;

import com.example.catfoodv1.model.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleCode(String roleCode);
}
