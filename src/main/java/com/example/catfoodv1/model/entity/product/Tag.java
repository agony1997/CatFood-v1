package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 產品標籤實體
 * 表格名稱: tag
 * 表格用途: 儲存產品的標籤，例如 "無穀物"、"幼貓專用"，用於篩選和分類。
 */
@EqualsAndHashCode(of = "tagCode", callSuper = false)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tag")
public class Tag extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 標籤的唯一標識符 (UUID)。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: tag_code
     * 欄位用途: 標籤的唯一業務代碼，用於內部識別 (例如 "GRAIN_FREE")。
     */
    @NotNull
    @Column(name = "tag_code", unique = true, nullable = false)
    private String tagCode;

    /**
     * 欄位名稱: tag_name
     * 欄位用途: 標籤的顯示名稱 (例如 "無穀物")。
     */
    @NotNull
    @Column(name = "tag_name", nullable = false)
    private String tagName;

    /**
     * 欄位名稱: N/A (由 product_tag 中介表格管理)
     * 欄位用途: 關聯到使用此標籤的所有產品。
     */
    @ManyToMany(mappedBy = "tags")
    private List<Product> products = new ArrayList<>(); // 關聯的產品
}