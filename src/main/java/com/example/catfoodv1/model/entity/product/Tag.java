package com.example.catfoodv1.model.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name; // 標籤名稱

    @ManyToMany(mappedBy = "tags")
    private List<Product> products = new ArrayList<>(); // 關聯的產品
}