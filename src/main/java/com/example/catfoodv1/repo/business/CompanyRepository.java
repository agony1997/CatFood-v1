package com.example.catfoodv1.repo.business;

import com.example.catfoodv1.model.entity.business.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
