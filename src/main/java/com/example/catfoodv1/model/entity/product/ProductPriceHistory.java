package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.business.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"variant", "store"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_price_history")
public class ProductPriceHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant variant; // 關聯的產品規格

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id") // Can be nullable if some prices are not store-specific
    private Store store; // 販售商店

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 價格

}