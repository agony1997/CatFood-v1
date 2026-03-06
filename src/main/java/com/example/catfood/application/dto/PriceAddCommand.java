package com.example.catfood.application.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PriceAddCommand {
    private UUID productId;
    private UUID variantId;
    private UUID storeId;
    private BigDecimal price;
}
