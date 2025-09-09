package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 產品詳細資訊實體
 * 表格名稱: product_detail
 * 表格用途: 儲存產品規格 (ProductVariant) 的詳細資訊，例如成分、營養分析、餵食指南等。
 */
@Getter
@Setter
@ToString(exclude = "variant")
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_detail")
public class ProductDetail extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 產品詳細資訊的唯一標識符 (UUID)。與 ProductVariant 共享主鍵。
     */
    @Id
    private UUID id;

    /**
     * 欄位名稱: product_variant_id
     * 欄位用途: 關聯到產品規格 (ProductVariant) 的外部索引鍵。實現一對一共享主鍵。
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // 將此實體的主鍵(id)映射到 variant 欄位，實現共享主鍵
    @JoinColumn(name = "product_variant_id")
    private ProductVariant variant;

    /**
     * 欄位名稱: ingredients
     * 欄位用途: 產品的詳細成分列表 (文本形式)。
     */
    @Lob // 表示這是一個大型物件，對應資料庫的 CLOB 或 TEXT 類型
    @Column(name = "ingredients")
    private String ingredients; // 食物成分

    // --- 營養分析 (Guaranteed Analysis) ---

    /**
     * 欄位名稱: protein_percentage
     * 欄位用途: 蛋白質含量百分比。
     */
    @Column(name = "protein_percentage", precision = 5, scale = 2)
    private BigDecimal proteinPercentage; // 蛋白質 (%)

    /**
     * 欄位名稱: fat_percentage
     * 欄位用途: 脂肪含量百分比。
     */
    @Column(name = "fat_percentage", precision = 5, scale = 2)
    private BigDecimal fatPercentage; // 脂肪 (%)

    /**
     * 欄位名稱: fiber_percentage
     * 欄位用途: 纖維含量百分比。
     */
    @Column(name = "fiber_percentage", precision = 5, scale = 2)
    private BigDecimal fiberPercentage; // 纖維 (%)

    /**
     * 欄位名稱: moisture_percentage
     * 欄位用途: 水分含量百分比。
     */
    @Column(name = "moisture_percentage", precision = 5, scale = 2)
    private BigDecimal moisturePercentage; // 水分 (%)

    /**
     * 欄位名稱: feeding_guide
     * 欄位用途: 產品的餵食指南 (文本形式)。
     */
    @Lob
    @Column(name = "feeding_guide")
    private String feedingGuide; // 餵食指南
}