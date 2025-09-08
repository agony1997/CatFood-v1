package com.example.catfoodv1.repo.business;

import com.example.catfoodv1.model.entity.business.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
