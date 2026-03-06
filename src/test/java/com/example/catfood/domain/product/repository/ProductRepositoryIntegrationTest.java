package com.example.catfood.domain.product.repository;

import com.example.catfood.domain.common.type.ProductType;
import com.example.catfood.domain.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ProductRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration/postgresql");
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllWithDetailsByType_loadsFullAggregate() {
        List<Product> products = productRepository.findAllWithDetailsByType(ProductType.WET_FOOD);

        assertFalse(products.isEmpty());
        Product first = products.get(0);
        assertNotNull(first.getProductCode());
        assertFalse(first.getFlavors().isEmpty());
        assertFalse(first.getFlavors().get(0).getVariants().isEmpty());
    }
}
