package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
