package com.example.catfood.domain.product.entity;

import com.example.catfood.domain.common.entity.Auditable;
import com.example.catfood.domain.common.type.PackageUnit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "sku", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variant")
public class Variant extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(name = "package_weight_grams")
    private Integer packageWeightGrams;

    @Column(name = "pack_size")
    private Integer packSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure")
    private PackageUnit unitOfMeasure;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "variant_id")
    private List<PriceHistory> priceHistories = new ArrayList<>();

    public void addPriceHistory(PriceHistory priceHistory) {
        this.priceHistories.add(priceHistory);
    }

    @PrePersist
    private void ensureSku() {
        if (this.sku == null || this.sku.isEmpty()) {
            this.sku = UUID.randomUUID().toString();
        }
    }
}
