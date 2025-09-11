package com.example.catfoodv1.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * 前端顯示 :
 * == Column Header ==
 * 1.品牌 = Brand.brandName
 * 2.名稱 = Product.productName + "-" + ProductVariant.variantName + "-" + ProductVariant.packSize + ProductVariant.unitOfMeasure.text + "裝"
 * EX : 鮮肉罐-田園火雞-1罐裝
 * 3.販售處 = Store.storeName
 * (ProductPriceHistory = list.sort(price).getFirst)
 * 4.價格 = ProductPriceHistory.price
 * 5.每100g單價 = ProductPriceHistory.price / ProductVariant.unitWeightGrams
 * 6.價格更新日 = ProductPriceHistory.updateDT
 * == 每行資料的明細下拉 (Item detail) (其餘的ProductPriceHistory)==
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WetFoodViewDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String storeName;
        private BigDecimal price;
        private BigDecimal pricePer;
        private LocalDateTime updateDT;
    }

    // === 顯示 ===
    private String brandName;
    private String displayName;
    private String storeName;
    private BigDecimal price;
    private BigDecimal pricePer; // 計算 : 每 100 克的價格
    private LocalDateTime updateDT;

    private Set<Detail> details;

    private UUID productId;
    private UUID variantId;

    // (價格 * 100) / 重量(g)
    public BigDecimal calculatePerPrice(Integer weight) {
        if (price != null && weight != null && weight > 0) {
            // 正確的公式：(價格 * 100) / 重量
            // 並指定保留 2 位小數，以及使用標準的四捨五入模式
            return this.pricePer = price.multiply(new BigDecimal("100"))
                    .divide(new BigDecimal(weight), 2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

}
