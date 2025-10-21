package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.auth.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

/**
 * 產品評論實體
 * 表格名稱: product_review
 * 表格用途: 儲存使用者對產品的評論與評分。
 */
@EqualsAndHashCode(of = {"product", "account"}, callSuper = false)
@Getter
@Setter
@ToString(exclude = {"product", "account"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "account_id"})
})
public class ProductReview extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 評論的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: product_id
     * 欄位用途: 關聯到被評論產品的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 關聯的產品

    /**
     * 欄位名稱: account_id
     * 欄位用途: 關聯到發表評論的使用者帳號的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // 評論者

    /**
     * 欄位名稱: rating
     * 欄位用途: 使用者給予的評分 (例如 1 到 5 星)。
     */
    private Integer rating; // 評分 (例如 1 到 5)

    /**
     * 欄位名稱: title
     * 欄位用途: 評論的標題。
     */
    private String title; // 標題

    /**
     * 欄位名稱: comment
     * 欄位用途: 評論的詳細文字內容。
     */
    @Lob // For longer text
    private String comment; // 評論內容

}
