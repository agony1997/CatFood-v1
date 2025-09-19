package com.example.catfoodv1.model.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * 產品規格與成分的關聯實體。
 * 表格名稱: product_variant_ingredient
 * 表格用途: 建立 ProductVariant 和 Ingredient 之間的多對多關聯，並儲存額外資訊，例如成分的順序。
 */
@Getter
@Setter
@ToString(exclude = "variant")
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variant_ingredient_mapping")
public class VariantIngredientMapping {

    /**
     * 欄位名稱: id
     * 欄位用途: 關聯的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 關聯到產品規格。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant variant;

    /**
     * 關聯到成分。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * 欄位名稱: ingredient_order
     * 欄位用途: 成分在此產品規格中的順序，數字越小代表越主要。
     */
    @Column(name = "ingredient_order")
    private int ingredientOrder;

}