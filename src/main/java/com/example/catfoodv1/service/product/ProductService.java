package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.repo.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private Product getEntityById(UUID id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product entity = getEntityById(product.getId());
        return productRepository.save(entity);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(getEntityById(id).getId());
    }

}
