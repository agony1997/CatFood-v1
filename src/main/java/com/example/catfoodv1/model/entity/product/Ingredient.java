package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * 產品主要成分實體
 * 表格名稱: ingredient
 * 表格用途: 儲存產品的各種成分，例如雞肉、鮭魚等，用於結構化篩選。
 */
@EqualsAndHashCode(of = "ingredientCode", callSuper = false)
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredient")
public class Ingredient extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 成分的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: ingredient_code
     * 欄位用途: 成分的唯一業務代碼，用於內部識別 (例如 "CHICKEN", "SALMON")。
     */
    @NotNull
    @Column(unique = true, nullable = false, length = 20)
    private String ingredientCode; // 成分代碼，例如 "CHICKEN", "SALMON"

    /**
     * 欄位名稱: ingredient_name
     * 欄位用途: 成分的顯示名稱 (例如 "雞肉", "鮭魚")。
     */
    @NotNull
    @Column(nullable = false)
    private String ingredientName; // 成分顯示名稱，例如 "雞肉", "鮭魚"
}