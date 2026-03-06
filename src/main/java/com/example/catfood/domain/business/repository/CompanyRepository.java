package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
