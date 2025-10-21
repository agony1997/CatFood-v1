package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.type.PackageUnit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 產品規格實體
 * 表格名稱: product_variant
 * 表格用途: 儲存產品的具體規格，例如不同口味、重量或包裝大小。每個規格都是一個獨立的 SKU (庫存單位)。
 */
@Getter
@Setter
@ToString(exclude = {"product", "detail", "priceHistory", "ingredientMappings"})
@EqualsAndHashCode(of = "sku", callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variant")
public class ProductVariant extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 產品規格的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: product_id
     * 欄位用途: 關聯到主產品 (Product) 的外部索引鍵。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * 欄位名稱: sku
     * 欄位用途: 庫存單位的唯一代碼 (Stock Keeping Unit)。
     */
    @Column(nullable = false, unique = true)
    private String sku;

    /**
     * 欄位名稱: variant_name
     * 欄位用途: 規格的顯示名稱，例如 "田園火雞" 或 "草飼牛肉"。
     */
    @Column(name = "variant_name")
    private String variantName;

    /**
     * 欄位名稱: package_weight_grams
     * 欄位用途: 包裝重量 (以克為單位)。
     */
    @Column(name = "package_weight_grams")
    private Integer packageWeightGrams;

    /**
     * 欄位名稱: pack_size
     * 欄位用途: 包裝內的數量，例如一箱 24 罐。
     */
    @Column(name = "pack_size")
    private Integer packSize;

    /**
     * 欄位名稱: unit_of_measure
     * 欄位用途: 計量單位，例如 "CAN" (罐) 或 "BAG" (袋)。
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure")
    private PackageUnit unitOfMeasure;

    /**
     * 欄位名稱: N/A (由 product_detail 表格的 product_variant_id 關聯)
     * 欄位用途: 與產品詳細資訊的一對一關聯。
     */
    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ProductDetail detail;

    /**
     * 欄位名稱: N/A (由 product_price_history 表格的 product_variant_id 關聯)
     * 欄位用途: 此規格的所有歷史價格紀錄。
     */
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPriceHistory> priceHistory = new ArrayList<>();

    /**
     * 關聯到中間實體 VariantIngredientMapping，這才是處理帶有額外欄位(ingredient_order)的多對多關聯的正確方式。
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_variant_id")
    @OrderBy("ingredientOrder ASC")
    private List<VariantIngredientMapping> ingredientMappings = new ArrayList<>();

    // == Helper Methods == //
    public void setDetail(ProductDetail detail) {
        if (detail != null) {
            this.detail = detail;
            detail.setVariant(this);
        }
    }

    public void addPriceHistory(ProductPriceHistory priceHistoryItem) {
        if (priceHistoryItem != null) {
            this.priceHistory.add(priceHistoryItem);
            priceHistoryItem.setVariant(this);
        }
    }

    public void addIngredientMapping(VariantIngredientMapping mapping) {
        if (mapping != null) {
            this.ingredientMappings.add(mapping);
            // 这是一个单向 @OneToMany，所以 mapping 对象不需要设置回引
        }
    }

    @PrePersist
    private void ensureSku() {
        if (this.sku == null || this.sku.isEmpty()) {
            this.sku = UUID.randomUUID().toString();
        }
    }
}
