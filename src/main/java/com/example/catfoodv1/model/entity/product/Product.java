package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.type.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 產品實體
 * 表格名稱: product
 * 表格用途: 儲存所有產品的共用資訊。
 */
@EqualsAndHashCode(of = "productCode", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"brand", "tags", "reviews", "variants"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 產品的唯一標識符 (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: product_code
     * 欄位用途: 產品的唯一代碼，用於業務邏輯。
     */
    @NotNull
    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode; // 產品代碼

    /**
     * 欄位名稱: product_name
     * 欄位用途: 產品的顯示名稱。
     */
    @NotNull
    @Column(name = "product_name")
    private String productName; // 產品名稱 例如: 鮮肉罐, 無穀田園系列

    /**
     * 欄位名稱: product_type
     * 欄位用途: 產品類型，例如 WET_FOOD, KIBBLE。
     */
    @NotNull
    @Enumerated(EnumType.STRING) // <-- 添加此行
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    /**
     * 欄位名稱: brand_id
     * 欄位用途: 關聯到品牌的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand; // 品牌

    /**
     * 欄位名稱: N/A (中介表格 product_tag)
     * 欄位用途: 產品與標籤的多對多關聯。
     */
    @ManyToMany
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>(); // 標籤

    /**
     * 欄位名稱: N/A (由 product_review 表格的 product_id 關聯)
     * 欄位用途: 產品的所有評論。
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>(); // 產品評論

    /**
     * 欄位名稱: N/A (由 product_variant 表格的 product_id 關聯)
     * 欄位用途: 產品的各種規格 (SKU)，例如不同重量或包裝。
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>(); // 產品規格 (SKUs)

    // == Helper Method == //
    public void addVariant(ProductVariant variant) {
        if (variant != null) {
            this.variants.add(variant);
            variant.setProduct(this);
        }
    }
}
