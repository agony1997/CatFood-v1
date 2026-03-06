package com.example.catfood.application.dto;

import com.example.catfood.domain.common.type.PackageUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WetFoodViewDto {
    private UUID productId;
    private UUID variantId;
    private UUID priceId;
    private String brandName;
    private String displayName;  // 產品名 - 口味名
    private String storeName;
    private PackageUnit unit;
    private BigDecimal price;
    private BigDecimal pricePer;
    private LocalDateTime updateDT;
    private List<WetFoodViewDto> details = new ArrayList<>();
}
