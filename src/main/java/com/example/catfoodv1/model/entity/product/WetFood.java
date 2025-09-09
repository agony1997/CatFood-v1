package com.example.catfoodv1.model.entity.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 濕糧產品實體
 * 表格名稱: product (與 Product 共用)
 * 表格用途: 儲存濕糧產品的特定資訊。在目前的設計中，它沒有自己的欄位，但透過繼承共用 Product 的所有欄位。
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
/**
 * 區分值: WET_FOOD
 * 用途: 在 product 表格的 product_type 欄位中，標識這筆記錄為濕糧。
 */
@DiscriminatorValue("WET_FOOD")
public class WetFood extends Product {
}
