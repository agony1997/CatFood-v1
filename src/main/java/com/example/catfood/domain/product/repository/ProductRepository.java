package com.example.catfood.domain.product.repository;

import com.example.catfood.domain.common.type.ProductType;
import com.example.catfood.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.flavors f " +
           "LEFT JOIN FETCH f.variants v " +
           "LEFT JOIN FETCH v.priceHistories " +
           "WHERE p.productType = :type")
    List<Product> findAllWithDetailsByType(@Param("type") ProductType type);

    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.flavors f " +
           "LEFT JOIN FETCH f.variants v " +
           "LEFT JOIN FETCH v.priceHistories")
    List<Product> findAllWithDetails();
}
