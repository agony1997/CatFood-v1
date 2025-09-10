package com.example.catfoodv1.model.dto.product;

import com.example.catfoodv1.model.type.PackageUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

    // --- 產品資訊 ---
    @NotNull(message = "必須選擇一個品牌")
    private UUID brandId;

    @NotBlank(message = "產品名稱不能為空")
    private String productName;

    // --- 規格資訊 ---
    @NotBlank(message = "規格名稱不能為空")
    private String variantDisplayName;

    @NotNull(message = "包裝數量不能為空")
    @Positive(message = "包裝數量必須為正數")
    private Integer packSize;

    @NotNull(message = "必須選擇包裝單位")
    private PackageUnit unitOfMeasure;

    // --- 初始價格資訊 ---
    @NotNull(message = "必須選擇一個商店")
    private UUID storeId;

    @NotNull(message = "價格不能為空")
    @Positive(message = "價格必須為正數")
    private BigDecimal price;

}
