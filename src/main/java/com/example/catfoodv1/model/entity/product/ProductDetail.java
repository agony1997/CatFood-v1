package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "variant")
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_detail")
public class ProductDetail extends Auditable {
    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // 將此實體的主鍵(id)映射到 variant 欄位，實現共享主鍵
    @JoinColumn(name = "product_variant_id")
    private ProductVariant variant;

    @Lob // 表示這是一個大型物件，對應資料庫的 CLOB 或 TEXT 類型
    @Column(name = "ingredients")
    private String ingredients; // 食物成分

    // --- 營養分析 (Guaranteed Analysis) ---

    @Column(name = "protein_percentage", precision = 5, scale = 2)
    private BigDecimal proteinPercentage; // 蛋白質 (%)

    @Column(name = "fat_percentage", precision = 5, scale = 2)
    private BigDecimal fatPercentage; // 脂肪 (%)

    @Column(name = "fiber_percentage", precision = 5, scale = 2)
    private BigDecimal fiberPercentage; // 纖維 (%)

    @Column(name = "moisture_percentage", precision = 5, scale = 2)
    private BigDecimal moisturePercentage; // 水分 (%)

    @Lob
    @Column(name = "feeding_guide")
    private String feedingGuide; // 餵食指南
}