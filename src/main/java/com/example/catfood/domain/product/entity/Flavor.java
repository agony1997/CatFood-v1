package com.example.catfood.domain.product.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flavor")
public class Flavor extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "flavor_name", nullable = false)
    private String flavorName;

    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "protein_percentage", precision = 5, scale = 2)
    private BigDecimal proteinPercentage;

    @Column(name = "fat_percentage", precision = 5, scale = 2)
    private BigDecimal fatPercentage;

    @Column(name = "moisture_percentage", precision = 5, scale = 2)
    private BigDecimal moisturePercentage;

    @Column(name = "carbs_percentage", precision = 5, scale = 2)
    private BigDecimal carbsPercentage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "flavor_id")
    private List<Variant> variants = new ArrayList<>();

    public void addVariant(Variant variant) {
        this.variants.add(variant);
    }
}
