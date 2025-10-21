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

    @NotBlank(message = "產品名稱不能為空")
    private String productName;

    @NotBlank(message = "規格名稱不能為空")
    private String flavorName;

    @NotNull(message = "價格不能為空")
    @Positive(message = "價格必須為正數")
    private BigDecimal price;

    @NotNull(message = "數量不能為空")
    @Positive(message = "數量必須為正數")
    private Integer packSize;

    @NotNull(message = "克數不能為空")
    @Positive(message = "克數必須為正數")
    private Integer grams;

    @NotNull(message = "必須選擇包裝單位")
    private PackageUnit unitOfMeasure;

    @NotNull(message = "必須選擇一個商店")
    private UUID storeId;
    @NotNull(message = "必須選擇一個品牌")
    private UUID brandId;
    @NotNull(message = "必須選擇一個主成分")
    private UUID ingredientId;

    private String productCode;
    private String ingredients; // 食物成分描述...
    private BigDecimal proteinPercentage; // 蛋白質 (%)
    private BigDecimal fatPercentage; // 脂肪 (%)
    private BigDecimal moisturePercentage; // 水分 (%)
    private BigDecimal carbsPercentage; // 碳水 (%)

}
