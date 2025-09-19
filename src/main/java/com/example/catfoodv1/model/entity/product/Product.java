package com.example.catfoodv1.model.entity.product;

import com.example.catfoodv1.model.Auditable;
import com.example.catfoodv1.model.entity.business.Brand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 產品實體
 * 表格名稱: product
 * 表格用途: 儲存所有產品的共用資訊，包含乾糧、濕糧等。採用單表繼承策略。
 */
@EqualsAndHashCode(of = "productCode", callSuper = false)
@Getter
@Setter
@ToString(exclude = {"brand", "tags", "reviews", "variants"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product extends Auditable {
    /**
     * 欄位名稱: id
     * 欄位用途: 產品的唯一標識符 (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 欄位名稱: product_code
     * 欄位用途: 產品的唯一代碼，用於業務邏輯。
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String productCode; // 產品代碼

    /**
     * 欄位名稱: product_name
     * 欄位用途: 產品的顯示名稱。
     */
    @NotNull
    private String productName; // 產品名稱 例如: 鮮肉罐, 無穀田園系列

    /**
     * 欄位名稱: brand_id
     * 欄位用途: 關聯到品牌的外部索引鍵。
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand; // 品牌

    /**
     * 欄位名稱: N/A (中介表格 product_tag)
     * 欄位用途: 產品與標籤的多對多關聯。
     */
    @ManyToMany
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>(); // 標籤

    /**
     * 欄位名稱: N/A (由 product_review 表格的 product_id 關聯)
     * 欄位用途: 產品的所有評論。
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>(); // 產品評論

    /**
     * 欄位名稱: N/A (由 product_variant 表格的 product_id 關聯)
     * 欄位用途: 產品的各種規格 (SKU)，例如不同重量或包裝。
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>(); // 產品規格 (SKUs)

    // == Helper Method == //
    public void addVariant(ProductVariant variant) {
        if (variant != null) {
            this.variants.add(variant);
            variant.setProduct(this);
        }
    }
}

//  JPA 的 @Inheritance 標籤用於指定實體類別的繼承結構如何映射到資料庫表格。
//  它與 @DiscriminatorColumn（用於區分子類別）或表格結構配合使用，適用於具有繼承關係的實體（如抽象類別 Product 及其子類別 DryFood 和 CannedFood）。

/*

SINGLE_TABLE（單表繼承）
    定義： 所有子類別的資料儲存在單一資料庫表格中，使用一個區分欄位（discriminator column）來標識每筆記錄的子類別型態。
          在您的程式碼中，使用 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
          和 @DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)。

    資料庫結構：單一表格（例如 product）包含所有父類別（Product）和子類別（DryFood、CannedFood）的欄位。
              區分欄位（product_type）儲存子類別的標識值（例如 "DRY" 或 "CANNED"）。

優點 : 1.高效查詢：所有資料在單一表格，查詢無需聯表（JOIN），效能高。
      2.簡單結構：表格數量少，易於管理。

      適用場景：子類別屬性差異小（如飼料和罐頭共用名稱、品牌、價格歷史等）

缺點： 1.空值問題：子類別專用欄位在其他子類別的記錄中可能為 NULL（如 weight 對 CannedFood 無用）。
      2.表格膨脹：若子類別數量或欄位差異增加，表格可能變得複雜。

*/

/*

TABLE_PER_CLASS（每類別一表繼承）

定義： 每個子類別對應一個獨立的資料庫表格，父類別的屬性複製到每個子類別的表格中。
      父類別（Product）不對應任何表格。
      使用方式：@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)。

資料庫結構: 每個子類別（如 DryFood、CannedFood）有自己的表格，
          包含父類別的所有欄位（id、productCode、productName、brand_id）以及子類別專屬欄位。
          不需要區分欄位（@DiscriminatorColumn 無效）。

優點： 1.無空值問題：每個表格僅包含相關欄位，結構清晰。
      2.靈活性：子類別可有完全不同的欄位，無需共用結構。


缺點: 1.重複欄位：父類別的欄位（如 productName、brand_id）在每個子類別表格中重複，增加維護成本。
     2.查詢複雜性：查詢所有產品（Product 類型）需要聯集（UNION）所有子類別表格，效能較差。
     3.不支援多型查詢：無法直接透過父類別查詢所有子類別記錄（需要特殊處理）。

與系統需求的適用性：
    不太適合：您的系統需要頻繁查詢所有產品（例如在 Vaadin Grid 中顯示所有飼料和罐頭），TABLE_PER_CLASS 的聯集查詢會增加複雜性。
            此外，priceHistory 和 tags 的多對多關聯需要額外處理，因子類別表格分開。
    適用場景：若 DryFood 和 CannedFood 的屬性差異極大（如飼料有獨特的包裝規格，罐頭有獨特的營養成分），且查詢多針對單一子類別，則可考慮此策略。
    未來擴展：爬蟲和趨勢圖功能需要為每個子類別表格分別實現邏輯，增加開發複雜度。

 */

/*

JOINED（聯表繼承）

定義:  父類別和每個子類別各對應一個資料庫表格。
      父類別表格儲存共用屬性，子類別表格儲存專屬屬性，透過主鍵（外鍵）關聯。

使用方式：@Inheritance(strategy = InheritanceType.JOINED)。
        可選擇性使用 @DiscriminatorColumn 來區分子類別（非必須）。

資料庫結構：父類別（Product）對應一個表格，儲存共用欄位。
         每個子類別（DryFood、CannedFood）對應獨立表格，儲存專屬欄位，並透過主鍵與父類別表格關聯。

優點：1.正規化：共用屬性儲存在父類別表格，避免重複。
     2.靈活性：子類別表格可獨立擴展欄位，適合屬性差異大的場景。
     3.支援多型查詢：可透過父類別（Product）查詢所有子類別記錄，JPA 自動處理聯表（JOIN）。


缺點：1.查詢效能：需要聯表查詢（JOIN），在資料量大時可能影響效能。
     2.結構複雜：表格數量較多，資料庫管理成本較高。

與系統需求的適用性：
    部分適合：若 DryFood 和 CannedFood 的專屬屬性較多（如飼料有重量、顆粒大小，罐頭有口味、濕度），
                         JOINED 策略可保持資料正規化，且支援 Vaadin Grid 顯示所有產品（透過父類別查詢）。
    挑戰：聯表查詢可能影響 Vaadin UI 的響應速度，尤其在顯示價格歷史或標籤時。需優化查詢（例如索引）或使用快取。
    未來擴展：爬蟲和趨勢圖功能可直接基於父類別表格（product）操作，但需注意子類別表格的資料整合。

 */