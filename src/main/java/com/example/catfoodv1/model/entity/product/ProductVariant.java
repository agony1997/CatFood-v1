package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.type.PackageUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

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
    // @NotNull - SKU is generated before persisting, so it can be null initially.
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    /**
     * 欄位名稱: variant_display_name
     * 欄位用途: 口味名稱 ex: 田園火雞
     */
    @Column(name = "variant_name")
    private String variantName;

    // 舊的 @ManyToMany 關聯已不適用
    // @ManyToMany
    // @JoinTable(name = "product_variant_ingredient", joinColumns = @JoinColumn(name = "product_variant_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    // private List<Ingredient> ingredients = new ArrayList<>(); // 主要成分

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("ingredientOrder ASC") // 核心：確保取出的成分列表永遠是按順序排列的
    private List<VariantIngredientMapping> variantIngredients = new ArrayList<>();
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

    /**
     * 在實體持久化或更新前自動產生 SKU。
     * 這是確保 SKU 格式一致性與唯一性的核心業務邏輯。
     * 格式: [ProductCode]-[VariantIdentifier]-[PackageIdentifier]
     * 範例: DOGCAT-001-TUR-85G1C
     */
    @PrePersist
    @PreUpdate
    public void generateSku() {
        // 確保所有必要資訊都存在，否則無法產生 SKU
        if (product == null || product.getProductCode() == null ||
                variantIngredients == null || variantIngredients.isEmpty() ||
                packageWeightGrams == null || packSize == null || unitOfMeasure == null) {
             throw new IllegalStateException("Cannot generate SKU due to missing variant properties.");
        }

        // 1. 產品代碼
        String productCode = product.getProductCode();

        // 2. 規格識別碼 (現在可以精確地取得第一主成分)
        String variantIdentifier = variantIngredients.getFirst().getIngredient().getIngredientCode();
        // 3. 包裝識別碼 (例如: 85G1C for 85 grams, 1 Can)
        String packageIdentifier = (packageWeightGrams / packSize) + "G" + packSize + unitOfMeasure.name().charAt(0);

        this.sku = String.join("-", productCode, variantIdentifier, packageIdentifier);
    }
}