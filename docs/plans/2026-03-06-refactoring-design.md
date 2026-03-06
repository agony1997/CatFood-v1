# CatFood-v1 重構設計文件

## 需求背景

貓用品價格追蹤與評價平台，先針對罐頭、飼料、貓砂。
用戶可建立貓咪角色，以貓的視角留言評價，養多隻貓也能各自留評。

## 執行策略

一次到位：在現有 repo 內全面重寫，不做漸進式 migration。

## 核心架構決定

- Lite DDD（Aggregate Root + Domain Service）
- 業務邏輯下沉到 Entity / Domain Service
- 單一 PostgreSQL，移除 H2 / MySQL
- 繼續使用 Vaadin 作為前端框架

---

## Entity 模型

### Aggregate 劃分

| Aggregate | Root | 包含 |
|---|---|---|
| Product | Product | Flavor, Variant, PriceHistory |
| Account | Account | CatProfile |
| Review | ProductReview | (獨立) |
| Brand | Brand | (獨立參考資料) |
| Company | Company | (獨立參考資料) |
| Store | Store | (獨立參考資料) |
| Tag | Tag | (獨立參考資料) |

### 關聯策略

- Aggregate 內部：使用 JPA 關聯標註（@OneToMany + cascade）
- 跨 Aggregate：只存 ID（UUID），不使用 JPA 關聯

### Entity 定義

```
domain.auth
  Account (id, accountCode, email, displayName, password)
    └─ CatProfile (id, catName, account_id)         [新增]

domain.business
  Company (id, companyCode, companyName)
  Brand (id, brandCode, brandName, companyId nullable) [company 改為選填]
  Store (id, storeCode, storeName, websiteUrl)

domain.product
  Product (id, productCode, productName, productType, brandId)
    ├─ tags: ManyToMany -> Tag
    └─ flavors: OneToMany -> Flavor

  Flavor (id, flavorName, ingredients TEXT,            [新增]
          proteinPercentage, fatPercentage,
          moisturePercentage, carbsPercentage)
    └─ variants: OneToMany -> Variant

  Variant (id, sku, packageWeightGrams, packSize, unitOfMeasure)
    └─ priceHistory: OneToMany -> PriceHistory

  PriceHistory (id, variantId, storeId, price)
  Tag (id, tagCode, tagName)

  ProductReview (id, productId, catProfileId,          [改關聯]
                rating, title, comment)
    unique constraint: (productId, catProfileId)
```

### 移除的 Entity

| 移除 | 替代方案 |
|---|---|
| Ingredient | Tag（篩選用）+ Flavor.ingredients TEXT（閱讀用） |
| VariantIngredientMapping | 同上 |
| ProductDetail | 合併進 Flavor |

### Product 層級結構

```
Product (產品線，例如「汪喵星球 鮮肉罐」)
  └─ Flavor (口味，例如「田園火雞」) ← 營養成分、成分文字在這
       └─ Variant (規格，例如 80g / 170g / 24入箱) ← 包裝、價格在這
            └─ PriceHistory (價格紀錄，append-only)
```

---

## Package 結構

```
com.example.catfood
├─ domain
│   ├─ product
│   │   ├─ entity        (Product, Flavor, Variant, PriceHistory)
│   │   ├─ service       (PriceDomainService)
│   │   └─ repository    (ProductRepository)
│   │
│   ├─ review
│   │   ├─ entity        (ProductReview)
│   │   └─ repository    (ProductReviewRepository)
│   │
│   ├─ account
│   │   ├─ entity        (Account, CatProfile, Role)
│   │   └─ repository    (AccountRepository)
│   │
│   ├─ business
│   │   ├─ entity        (Brand, Company, Store)
│   │   └─ repository    (BrandRepository, CompanyRepository, StoreRepository)
│   │
│   └─ common
│       ├─ type          (ProductType, PackageUnit, WeightUnit)
│       └─ entity        (Tag + TagRepository)
│
├─ application
│   ├─ ProductApplicationService.java
│   ├─ ReviewApplicationService.java
│   └─ dto
│       ├─ ProductCreateCommand.java
│       ├─ PriceAddCommand.java
│       ├─ WetFoodViewDto.java
│       └─ ...
│
├─ infrastructure
│   ├─ config            (SecurityConfig, JpaAuditingConfiguration, PersistenceConfig)
│   └─ aspect            (LoggingAspect, NoLogging)
│
└─ view
    ├─ MainLayout.java
    ├─ LoginView.java
    ├─ wet               (WetFoodView, CreateDialog)
    ├─ kibble            (KibbleView)
    ├─ sand              (SandView)
    └─ component         (BaseDialog, FormPopover, select/...)
```

### 各層職責

| 層 | 職責 | 不該做的事 |
|---|---|---|
| domain | 業務邏輯、Entity、Domain Service | 不碰 DTO、不碰 UI |
| application | 編排 domain 呼叫、DTO 轉換、transaction 邊界 | 不放業務邏輯 |
| infrastructure | 框架設定、切面、外部整合 | 不放業務邏輯 |
| view | Vaadin UI、使用者互動 | 不直接碰 Repository |

### Repository 原則

只有 Aggregate Root 和獨立參考資料有 Repository：
- ProductRepository, ProductReviewRepository, AccountRepository
- BrandRepository, CompanyRepository, StoreRepository, TagRepository
- Flavor, Variant, PriceHistory, CatProfile 透過 Aggregate Root cascade 存取

---

## Domain Service 分工

### PriceDomainService（純業務邏輯，不碰 DB）

- findLowestPrice(histories) - 找最低價
- calculatePerHundredGrams(price, weight) - 算百克價

### Application Service（編排 + DTO 轉換）

| 操作 | Application Service | Domain Service |
|---|---|---|
| 新增產品 | 驗證 brandId 存在、組裝 Entity、save | - |
| 新增價格 | 找到 Variant、save | - |
| 查詢列表 | 呼叫 repo + domain service、組 DTO | 找最低價、算百克價 |
| 留評論 | 驗證 productId/catProfileId 存在、save | - |

---

## 查詢優化

- Repository 使用 JOIN FETCH 解決 N+1 問題
- 保留 @Cacheable 機制作為第一道防線
- 價格模型維持 append-only，不加 current_price 欄位

---

## Migration 策略

### Flyway

- 刪除所有舊 V*.sql migration 檔
- 寫全新 V1__init.sql 包含完整新 schema
- 開發環境用 flyway.baseline-on-migrate=true

### 依賴清理 (pom.xml)

| 動作 | 項目 |
|---|---|
| 移除 | com.h2database:h2 |
| 移除 | com.mysql:mysql-connector-j |
| 保留 | org.postgresql:postgresql 及其他所有依賴 |
