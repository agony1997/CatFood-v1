package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.type.PackageUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 產品規格實體 (SKU)
 * 表格名稱: product_variant
 * 表格用途: 儲存產品的具體規格，例如不同口味、重量或包裝的組合。
 */
@EqualsAndHashCode(of = "sku", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"product", "priceHistory", "detail"})
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
     * 欄位用途: 關聯到主產品的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * 欄位名稱: sku
     * 欄位用途: 庫存單位 (Stock Keeping Unit)，產品規格的唯一業務代碼。
     */
    @NotNull
    @Column(name = "sku", unique = true, nullable = false)
    private String sku; // 建議格式: productCode-packageInfo (不再包含口味)

    /**
     * 欄位名稱: variant_display_name
     * 欄位用途: 規格的顯示名稱，用於行銷 (例如: "幼貓雞肉", "八種魚配方")。
     */
    @Column(name = "variant_display_name")
    private String variantDisplayName; // 規格的顯示/行銷名稱 (例如: "幼貓雞肉", "八種魚配方")

    /**
     * 欄位名稱: N/A (中介表格 product_variant_ingredient)
     * 欄位用途: 此規格包含的主要成分。
     */
    @ManyToMany
    @JoinTable(name = "product_variant_ingredient", joinColumns = @JoinColumn(name = "product_variant_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>(); // 主要成分

    /**
     * 欄位名稱: package_weight_grams
     * 欄位用途: 包裝的總重量 (公克)。
     */
    @Column(name = "package_weight_grams")
    private Integer packageWeightGrams; // 包裝總重量 (公克)

    /**
     * 欄位名稱: pack_size
     * 欄位用途: 包裝內含的單位數量 (例如一箱24罐，此處為24；單一包裝則為1)。
     */
    @Column(name = "pack_size")
    private Integer packSize; // 包裝內含數量 (例如一箱24罐，此處為24；單一包裝則為1)

    /**
     * 欄位名稱: unit_of_measure
     * 欄位用途: 包裝的計量單位 (例如: 'CAN', 'BAG', 'POUCH')。
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure")
    private PackageUnit unitOfMeasure; // 包裝單位 (例如: 'CAN', 'BAG', 'POUCH')

    /**
     * 欄位名稱: N/A (由 product_price_history 表格的 product_variant_id 關聯)
     * 欄位用途: 此規格的所有歷史價格記錄。
     */
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductPriceHistory> priceHistory = new ArrayList<>(); // 價格歷史

    /**
     * 欄位名稱: N/A (由 product_detail 表格的 variant_id 關聯)
     * 欄位用途: 此規格的詳細資訊 (例如營養分析、保證分析等)。
     */
    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail detail; // 產品詳細資訊

    /**
     * 輔助方法，用於同步 ProductDetail 的雙向關聯。
     * 當設置 detail 時，同時更新 detail 中的 variant 引用。
     * @param detail 要關聯的產品詳細資訊實體
     */
    public void setDetail(ProductDetail detail) {
        if (detail == null) {
            if (this.detail != null) {
                this.detail.setVariant(null);
            }
        } else {
            detail.setVariant(this);
        }
        this.detail = detail;
    }
}