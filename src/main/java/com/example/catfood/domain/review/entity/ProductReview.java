package com.example.catfood.domain.review.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_review",
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "cat_profile_id"}))
public class ProductReview extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @NotNull
    @Column(name = "cat_profile_id", nullable = false)
    private UUID catProfileId;

    @Column
    private Integer rating;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
