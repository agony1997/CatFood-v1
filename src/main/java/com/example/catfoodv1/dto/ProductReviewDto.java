package com.example.catfoodv1.dto;

import com.example.catfoodv1.model.entity.product.ProductReview;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductReviewDto {
    private UUID id;
    private Integer rating;
    private String title;
    private String comment;
    private LocalDateTime createdAt;
    private String authorUsername; // 作者的顯示名稱

    public static ProductReviewDto fromEntity(ProductReview review) {
        if (review == null) {
            return null;
        }
        ProductReviewDto dto = new ProductReviewDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        // 從關聯的 User 實體中獲取顯示名稱
        if (review.getUser() != null) {
            dto.setAuthorUsername(review.getUser().getUsername());
        }
        return dto;
    }
}
