package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "sku", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"product", "priceHistory", "detail"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variant")
public class ProductVariant extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "sku", unique = true, nullable = false)
    private String sku; // Stock Keeping Unit，唯一的庫存單位碼

    @Column(name = "flavor")
    private String flavor; // 口味

    @Column(name = "package_weight_kg", precision = 10, scale = 3)
    private BigDecimal packageWeightKg; // 包裝總重量 (公斤)

    @Column(name = "pack_size")
    private Integer packSize; // 包裝內含數量 (例如一箱24罐，此處為24；單一包裝則為1)

    @Column(name = "unit_of_measure")
    private String unitOfMeasure; // 包裝單位 (例如: 'can', 'bag', 'pouch')

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductPriceHistory> priceHistory = new ArrayList<>(); // 價格歷史

    @OneToOne(mappedBy = "variant", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ProductDetail detail; // 產品詳細資訊

}