package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
