package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.business.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * 產品價格歷史實體
 * 表格名稱: product_price_history
 * 表格用途: 記錄產品不同規格在不同商店的歷史價格。
 */
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"variant", "store"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_price_history", indexes = {@Index(name = "idx_price_history_query",
        columnList = "product_variant_id, store_id, create_dt")})
public class ProductPriceHistory extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 價格歷史記錄的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: product_variant_id
     * 欄位用途: 關聯到特定產品規格 (SKU) 的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant variant; // 關聯的產品規格

    /**
     * 欄位名稱: store_id
     * 欄位用途: 關聯到販售商店的外部索引鍵 (可為空，若價格非特定商店)。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id") // Can be nullable if some prices are not store-specific
    private Store store; // 販售商店

    /**
     * 欄位名稱: price
     * 欄位用途: 記錄的價格。
     */
    @NotNull
    @Column(nullable = false)
    private Integer price; // 價格

}