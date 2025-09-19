package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.dto.product.ProductCreateDto;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Store;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

/**
 * 濕糧產品實體
 * 表格名稱: product (與 Product 共用)
 * 表格用途: 儲存濕糧產品的特定資訊。在目前的設計中，它沒有自己的欄位，但透過繼承共用 Product 的所有欄位。
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Builder
@AllArgsConstructor
/*
 * 區分值: WET_FOOD
 * 用途: 在 product 表格的 product_type 欄位中，標識這筆記錄為濕糧。
 */
@DiscriminatorValue("WET_FOOD")
public class WetFood extends Product {

    /**
     * 靜態工廠方法：根據 DTO 建立一個完整的濕食產品及其所有關聯物件。
     */
    public static WetFood create(ProductCreateDto dto, Brand brand, Ingredient ingredient, Store store) {
        // 1. 建立主要實體
        WetFood product = new WetFood();
        product.setBrand(brand);
        product.setProductCode(UUID.randomUUID().toString().substring(0, 8));
        product.setProductName(dto.getProductName());

        // 2. 建立關聯的子實體
        ProductVariant variant = new ProductVariant();
        variant.setPackSize(dto.getPackSize());
        variant.setVariantName(dto.getFlavorName());
        variant.setUnitOfMeasure(dto.getUnitOfMeasure());
        variant.setPackageWeightGrams(dto.getGrams());

        ProductDetail detail = new ProductDetail();
        detail.setMoisturePercentage(dto.getMoisturePercentage());
        detail.setProteinPercentage(dto.getProteinPercentage());
        detail.setFatPercentage(dto.getFatPercentage());
        detail.setCarbsPercentage(dto.getCarbsPercentage());
        // 使用輔助方法確保雙向關聯
        variant.setDetail(detail);

        // 同樣，建立物件後使用輔助方法加入集合
        VariantIngredientMapping mapping = new VariantIngredientMapping();
        mapping.setIngredient(ingredient);
        mapping.setIngredientOrder(1);
        variant.addIngredientMapping(mapping);

        ProductPriceHistory priceHistory = new ProductPriceHistory();
        priceHistory.setStore(store);
        priceHistory.setPrice(dto.getPrice());
        variant.addPriceHistory(priceHistory);

        // 3. 使用輔助方法將 Variant 加入 Product，自動處理雙向關聯
        product.addVariant(variant);

        // 4. 回傳組裝完成的根實體
        return product;
    }
}
