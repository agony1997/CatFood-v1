package com.example.catfoodv1.model.entity.business;

import com.example.catfoodv1.model.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name; // 品牌名稱

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; // 所屬公司

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>(); // 旗下產品
}
