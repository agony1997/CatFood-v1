package com.example.catfood.application.dto;

import com.example.catfood.domain.common.type.PackageUnit;
import com.example.catfood.domain.common.type.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductCreateCommand {
    private String productCode;
    private String productName;
    private ProductType productType;
    private UUID brandId;
    private List<UUID> tagIds;
    // Flavor info
    private String flavorName;
    private String ingredients;
    private BigDecimal proteinPercentage;
    private BigDecimal fatPercentage;
    private BigDecimal moisturePercentage;
    private BigDecimal carbsPercentage;
    // Variant info
    private String sku;
    private Integer packageWeightGrams;
    private Integer packSize;
    private PackageUnit unitOfMeasure;
    // Price info
    private UUID storeId;
    private BigDecimal price;
}
