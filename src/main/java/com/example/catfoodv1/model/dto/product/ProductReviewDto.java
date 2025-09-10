package com.example.catfoodv1.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDto {
    private String username; // 評論者顯示名稱
    private Integer rating; // 評分
    private String title; // 標題
    private String comment; // 評論內容
    private LocalDateTime reviewDate; // 評論日期
}