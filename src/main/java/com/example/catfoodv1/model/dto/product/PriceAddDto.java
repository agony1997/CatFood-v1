package com.example.catfoodv1.model.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceAddDto {
    @NotNull(message = "必須指定產品規格ID")
    private UUID variantId;

    @NotNull(message = "必須選擇商店")
    private UUID storeId;

    @NotNull(message = "價格不能為空")
    @Positive(message = "價格必須為正數")
    private Integer price;
}