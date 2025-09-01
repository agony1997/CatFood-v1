package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.entity.auth.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 關聯的產品

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 評論者

    @Column(nullable = false)
    private Integer rating; // 評分 (例如 1 到 5)

    private String title; // 標題

    @Lob // For longer text
    private String comment; // 評論內容

    private LocalDateTime createdAt; // 建立時間
}
