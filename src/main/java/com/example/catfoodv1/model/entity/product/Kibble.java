package com.example.catfoodv1.model.entity.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * 乾糧產品實體
 * 表格名稱: product (與 Product 共用)
 * 表格用途: 儲存乾糧產品的特定資訊。在目前的設計中，它沒有自己的欄位，但透過繼承共用 Product 的所有欄位。
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
/**
 * 區分值: KIBBLE
 * 用途: 在 product 表格的 product_type 欄位中，標識這筆記錄為乾糧。
 */
@DiscriminatorValue("KIBBLE")
public class Kibble extends Product{

}
