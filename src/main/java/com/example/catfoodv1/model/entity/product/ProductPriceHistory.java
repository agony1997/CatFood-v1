package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.entity.business.Store;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product_price_history")
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 關聯的產品

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id") // Can be nullable if some prices are not store-specific
    private Store store; // 販售商店

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // 價格

    @Column(name = "effective_date", nullable = false)
    private LocalDateTime effectiveDate; // 價格生效日期

    @Column(name = "end_date")
    private LocalDateTime endDate; // 價格結束日期

    public ProductPriceHistory(Product product, BigDecimal price, LocalDateTime effectiveDate) {
        this.product = product;
        this.price = price;
        this.effectiveDate = effectiveDate;
    }
}