package com.example.catfoodv1.repo.product;

import com.example.catfoodv1.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
