package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.auth.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = {"product", "account"}, callSuper = false)
@Getter
@Setter
@ToString(exclude = {"product", "account"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_review")
public class ProductReview extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 關聯的產品

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // 評論者

    @NotNull
    @Column(nullable = false)
    private Integer rating; // 評分 (例如 1 到 5)

    private String title; // 標題

    @Lob // For longer text
    private String comment; // 評論內容

}
