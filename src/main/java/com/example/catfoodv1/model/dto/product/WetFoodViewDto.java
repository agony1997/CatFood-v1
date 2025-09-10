package com.example.catfoodv1.model.dto.product;

import com.example.catfoodv1.model.entity.business.Store;
import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.model.entity.product.ProductPriceHistory;
import com.example.catfoodv1.model.entity.product.ProductVariant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用於濕糧主視圖 Grid 的數據傳輸對象 (DTO)。
 * 此 DTO 採用扁平化設計，每一筆記錄代表一個特定商品規格在特定商店的最新價格資訊，
 * 旨在優化性能，避免在 Grid 中加載過多不必要的數據。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WetFoodViewDto {
    // --- 產品基本資訊 ---
    private UUID productId;
    private String productName;
    private String brandName;

    // --- 規格 (Variant) 資訊 ---
    private UUID variantId;
    private String variantDisplayName; // 規格的顯示名稱 (例如: "幼貓雞肉餐罐")
    private Integer unitWeightGrams; // 單一包裝重量 (公克)，整數
    private Integer packSize; // 包裝內含數量 (例如 24)
    private String unitOfMeasure; // 包裝單位 (例如: 'CAN', 'POUCH')

    // --- 價格資訊 ---
    private String storeName;
    private BigDecimal price;
    private LocalDateTime priceUpdateDT;

    // --- 計算欄位 ---
    private BigDecimal pricePer100g; // 每 100 克的價格

    /**
     * 靜態工廠方法，用於從 ProductPriceHistory 實體方便地創建 WetFoodViewDto。
     * 這種方式將轉換邏輯封裝在此 DTO 內部，保持服務層的整潔。
     *
     * @param priceHistory 包含最新價格資訊的實體
     * @return 一個填充好數據的 WetFoodViewDto 物件
     */
    public static WetFoodViewDto from(ProductPriceHistory priceHistory) {
        ProductVariant variant = priceHistory.getVariant();
        Product product = variant.getProduct();
        Store store = priceHistory.getStore();

        WetFoodViewDto dto = new WetFoodViewDto();
        dto.setProductId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setBrandName(product.getBrand().getBrandName());

        dto.setVariantId(variant.getId());
        dto.setVariantDisplayName(variant.getVariantDisplayName());
        dto.setPackSize(variant.getPackSize());
        dto.setUnitOfMeasure(variant.getUnitOfMeasure() != null ? variant.getUnitOfMeasure().name() : null);

        // 計算單一包裝重量 (公克) - 新邏輯
        if (variant.getPackageWeightGrams() != null && variant.getPackSize() != null && variant.getPackSize() > 0) {
            // 直接使用整數除法
            dto.setUnitWeightGrams(variant.getPackageWeightGrams() / variant.getPackSize());
        }

        dto.setStoreName(store != null ? store.getStoreName() : "通用"); // 如果商店為空，給予一個預設值
        dto.setPrice(priceHistory.getPrice());
        dto.setPriceUpdateDT(priceHistory.getCreateDT()); // 價格記錄的創建時間即為更新時間

        // 計算每 100g 的價格 - 新邏輯
        if (variant.getPackageWeightGrams() != null && variant.getPackageWeightGrams() > 0 && priceHistory.getPrice() != null) {
            BigDecimal weightInGrams = new BigDecimal(variant.getPackageWeightGrams());
            BigDecimal pricePerGram = priceHistory.getPrice().divide(weightInGrams, 4, RoundingMode.HALF_UP);
            dto.setPricePer100g(pricePerGram.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP));
        }

        return dto;
    }
}
