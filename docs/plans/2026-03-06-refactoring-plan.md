# CatFood-v1 Lite DDD 重構實作計畫

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** 將 CatFood-v1 從傳統分層架構重構為 Lite DDD 架構，新增 Flavor 層級和 CatProfile，並清理多餘依賴。

**Architecture:** Lite DDD — domain 層包含 Entity + Domain Service + Repository interface，application 層做薄編排和 DTO 轉換，infrastructure 放框架設定，view 放 Vaadin UI。Aggregate 內部用 JPA 關聯，跨 Aggregate 只存 ID。

**Tech Stack:** Spring Boot 3.3.1, Vaadin 24.7.7, PostgreSQL, Flyway, Testcontainers, Lombok, JPA/Hibernate

**設計文件:** `docs/plans/2026-03-06-refactoring-design.md`

---

## Task 1: 依賴清理與配置檔整理

**Files:**
- Modify: `pom.xml:49-57` (移除 H2 和 MySQL dependency)
- Delete: `src/main/resources/application-h2.properties`
- Delete: `src/main/resources/application-docker-mysql.properties`
- Delete: `src/main/resources/db/migration/mysql/` (整個資料夾)
- Modify: `src/main/resources/application.properties`

**Step 1: 修改 pom.xml**

從 `<dependencies>` 中移除以下兩個 dependency：
```xml
<!-- 移除 -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Step 2: 刪除多餘的配置檔**

刪除：
- `src/main/resources/application-h2.properties`
- `src/main/resources/application-docker-mysql.properties`

**Step 3: 刪除 MySQL migration 資料夾**

刪除整個 `src/main/resources/db/migration/mysql/` 目錄。

**Step 4: 清理 SecurityConfig 中的 H2 引用**

修改 `SecurityConfig.java`，移除 H2 console 相關的 CSRF ignore 和 frame options：

```java
// 移除這段
if (path.startsWith("/h2-console/")) {
    return true;
}

// 移除這行
http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
```

**Step 5: Commit**

```bash
git add -A
git commit -m "chore: 移除 H2/MySQL 依賴，單一支援 PostgreSQL"
```

---

## Task 2: 建立新的 Flyway Migration

**Files:**
- Delete: `src/main/resources/db/migration/postgresql/V1__create_auth_tables.sql`
- Delete: `src/main/resources/db/migration/postgresql/V2__create_business_tables.sql`
- Delete: `src/main/resources/db/migration/postgresql/V3__create_product_tables.sql`
- Delete: `src/main/resources/db/migration/postgresql/V4__insert_initial_data.sql`
- Create: `src/main/resources/db/migration/postgresql/V1__init_schema.sql`
- Create: `src/main/resources/db/migration/postgresql/V2__init_data.sql`

**Step 1: 刪除舊的 migration 檔案**

刪除 `src/main/resources/db/migration/postgresql/` 下所有 V1~V4 的 SQL 檔。

**Step 2: 建立 V1__init_schema.sql**

```sql
-- V1: Complete schema for CatFood Lite DDD architecture
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==========================================
-- Auth Domain
-- ==========================================
CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_code VARCHAR(50) NOT NULL UNIQUE,
    role_name VARCHAR(100) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE account (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_code VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE user_roles (
    account_id UUID NOT NULL REFERENCES account(id),
    role_id UUID NOT NULL REFERENCES role(id),
    PRIMARY KEY (account_id, role_id)
);

CREATE TABLE cat_profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cat_name VARCHAR(100) NOT NULL,
    account_id UUID NOT NULL REFERENCES account(id),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

-- ==========================================
-- Business Domain
-- ==========================================
CREATE TABLE company (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    company_code VARCHAR(255) NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE brand (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    brand_code VARCHAR(255) NOT NULL UNIQUE,
    brand_name VARCHAR(255) NOT NULL,
    company_id UUID REFERENCES company(id),  -- nullable
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE store (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    store_code VARCHAR(255) NOT NULL UNIQUE,
    store_name VARCHAR(255) NOT NULL,
    website_url VARCHAR(500),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

-- ==========================================
-- Product Domain
-- ==========================================
CREATE TABLE tag (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tag_code VARCHAR(255) NOT NULL UNIQUE,
    tag_name VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_code VARCHAR(255) NOT NULL UNIQUE,
    product_name VARCHAR(255) NOT NULL,
    product_type VARCHAR(31) NOT NULL,
    brand_id UUID NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE product_tag (
    product_id UUID NOT NULL REFERENCES product(id),
    tag_id UUID NOT NULL REFERENCES tag(id),
    PRIMARY KEY (product_id, tag_id)
);

CREATE TABLE flavor (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL REFERENCES product(id),
    flavor_name VARCHAR(255) NOT NULL,
    ingredients TEXT,
    protein_percentage DECIMAL(5,2),
    fat_percentage DECIMAL(5,2),
    moisture_percentage DECIMAL(5,2),
    carbs_percentage DECIMAL(5,2),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE variant (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    flavor_id UUID NOT NULL REFERENCES flavor(id),
    sku VARCHAR(255) NOT NULL UNIQUE,
    package_weight_grams INTEGER,
    pack_size INTEGER,
    unit_of_measure VARCHAR(31),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE price_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    variant_id UUID NOT NULL,
    store_id UUID NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_price_variant FOREIGN KEY (variant_id) REFERENCES variant(id),
    CONSTRAINT fk_price_store FOREIGN KEY (store_id) REFERENCES store(id)
);
CREATE INDEX idx_price_history_query ON price_history(variant_id, store_id, create_dt);

-- ==========================================
-- Review Domain
-- ==========================================
CREATE TABLE product_review (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    cat_profile_id UUID NOT NULL,
    rating INTEGER,
    title VARCHAR(255),
    comment TEXT,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT uq_review_product_cat UNIQUE (product_id, cat_profile_id),
    CONSTRAINT chk_rating CHECK (rating >= 1 AND rating <= 5)
);
```

**Step 3: 建立 V2__init_data.sql**

```sql
-- V2: Initial seed data

-- Roles
INSERT INTO role (id, role_code, role_name, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'ROLE_USER', '一般使用者', NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'ROLE_ADMIN', '管理員', NOW(), 'system', NOW(), 'system');

-- Accounts (password = '123')
INSERT INTO account (id, account_code, email, display_name, password, create_dt, creator, update_dt, updater) VALUES
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', 'admin', 'admin@example.com', 'Kuma', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system'),
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', 'normal', 'normal@example.com', 'cat', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system');

-- User Roles
INSERT INTO user_roles (account_id, role_id)
SELECT 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', id FROM role WHERE role_code = 'ROLE_USER';
INSERT INTO user_roles (account_id, role_id)
SELECT 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', id FROM role WHERE role_code = 'ROLE_ADMIN';
INSERT INTO user_roles (account_id, role_id)
SELECT 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', id FROM role WHERE role_code = 'ROLE_USER';

-- Cat Profiles
INSERT INTO cat_profile (id, cat_name, account_id, create_dt, creator, update_dt, updater) VALUES
('e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e001', '小橘', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e002', '賓士', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e003', '三花', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', NOW(), 'system', NOW(), 'system');

-- Stores
INSERT INTO store (id, store_code, store_name, create_dt, creator, update_dt, updater) VALUES
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a001', 'PC', 'PcHome', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 'MOMO', 'MoMo', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a003', 'COUP', '酷澎', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a004', 'SHOP', '蝦皮', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 'OLD', '老地方', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a006', 'BONE', 'BoneBone', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a007', 'LI66', '光66', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a008', 'LUCK', '好狗運', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a009', 'PARK', '寵物公園', NOW(), 'system', NOW(), 'system'),
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 'OFFICIAL', '官網', NOW(), 'system', NOW(), 'system');

-- Tags (含主要成分作為 tag)
INSERT INTO tag (id, tag_code, tag_name, create_dt, creator, update_dt, updater) VALUES
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001', 'GRAIN_FREE', '無穀', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a002', 'KITTEN', '幼貓專用', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a003', 'HAIRBALL_CONTROL', '化毛', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a010', 'CHICKEN', '雞肉', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a011', 'TURKEY', '火雞肉', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a012', 'BEEF', '牛肉', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a013', 'SALMON', '鮭魚', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a014', 'TUNA', '鮪魚', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a015', 'DEER', '鹿肉', NOW(), 'system', NOW(), 'system');

-- Company
INSERT INTO company (id, company_code, company_name, create_dt, creator, update_dt, updater) VALUES
('c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', 'SHEP', '牧羊人集團', NOW(), 'system', NOW(), 'system');

-- Brands
INSERT INTO brand (id, brand_code, brand_name, company_id, create_dt, creator, update_dt, updater) VALUES
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a001', 'DOGCAT', '汪喵星球', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'MONSTER', '怪獸部落', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a003', 'HEROMA', 'HeroMama', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system');

-- Products
INSERT INTO product (id, product_code, product_name, brand_id, product_type, create_dt, creator, update_dt, updater) VALUES
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'DOGCAT-001', '鮮肉罐', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a001', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'MONSTER-001', '怪獸海鮮系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', 'MONSTER-002', '怪獸陸地系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system');

-- Product Tags
INSERT INTO product_tag (product_id, tag_id) VALUES
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a011'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a012'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a013'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a014'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a010'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a012');

-- Flavors (口味)
INSERT INTO flavor (id, product_id, flavor_name, ingredients, protein_percentage, fat_percentage, moisture_percentage, carbs_percentage, create_dt, creator, update_dt, updater) VALUES
-- 汪喵星球 鮮肉罐
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f001', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '田園火雞', '火雞肉、雞心肝、雞蛋黃...', 15.1, 6.0, 75.9, NULL, NOW(), 'system', NOW(), 'system'),
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f002', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '草飼牛肉', '牛肉、雞肉、雞心肝、雞蛋黃...', 15.8, 6.6, 74.6, NULL, NOW(), 'system', NOW(), 'system'),
-- 怪獸部落 海鮮系列
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f003', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '怪獸鮭魚', '鮭魚、雞心肝、雞蛋黃...', 15.0, 6.0, 75.0, NULL, NOW(), 'system', NOW(), 'system'),
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f004', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '怪獸鮪魚', '鮪魚、雞心肝、雞蛋黃...', 15.2, 6.2, 74.8, NULL, NOW(), 'system', NOW(), 'system'),
-- 怪獸部落 陸地系列
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f005', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '怪獸雞肉', '雞肉、雞心肝、雞蛋黃...', 15.5, 6.5, 74.5, NULL, NOW(), 'system', NOW(), 'system'),
('f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f006', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '怪獸牛肉', '牛肉、雞心肝、雞蛋黃...', 16.0, 7.0, 74.0, NULL, NOW(), 'system', NOW(), 'system');

-- Variants (規格)
INSERT INTO variant (id, flavor_id, sku, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
-- 田園火雞: 單罐 + 箱
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f001', 'DOGCAT-001-TURKEY-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f001', 'DOGCAT-001-TURKEY-85G-24C', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
-- 草飼牛肉
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f002', 'DOGCAT-001-BEEF-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
-- 怪獸鮭魚
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f003', 'MONSTER-001-SALMON-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f003', 'MONSTER-001-SALMON-85G-24C', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
-- 怪獸鮪魚
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f004', 'MONSTER-001-TUNA-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
-- 怪獸雞肉
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f005', 'MONSTER-002-CHICKEN-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
-- 怪獸牛肉
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', 'f0f0f0f0-f0f0-f0f0-f0f0-f0f0f0f0f006', 'MONSTER-002-BEEF-85G-1C', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Price History
INSERT INTO price_history (id, variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
-- 田園火雞 單罐
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 46.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 45.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 43.0, NOW(), 'system', NOW(), 'system'),
-- 田園火雞 箱
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 1100.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 900.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 1000.0, NOW(), 'system', NOW(), 'system'),
-- 草飼牛肉
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 46.0, NOW(), 'system', NOW(), 'system'),
-- 怪獸鮭魚 單罐
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 50.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 48.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 45.0, NOW(), 'system', NOW(), 'system'),
-- 怪獸鮭魚 箱
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 1200.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 1000.0, NOW(), 'system', NOW(), 'system'),
-- 怪獸鮪魚
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 55.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 50.0, NOW(), 'system', NOW(), 'system'),
-- 怪獸雞肉
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 48.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 43.0, NOW(), 'system', NOW(), 'system'),
-- 怪獸牛肉
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 52.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 48.0, NOW(), 'system', NOW(), 'system');

-- Product Reviews (用貓咪角色留言)
INSERT INTO product_review (id, product_id, cat_profile_id, title, rating, comment, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e001', '火雞肉超好吃喵~', 5, '本喵一聞到就跑過來了！火雞肉很香，每次都吃光光！', NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e002', '還行吧', 3, '本喵比較挑嘴，火雞肉不是最愛，但肚子餓的時候會吃。', NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'e0e0e0e0-e0e0-e0e0-e0e0-e0e0e0e0e003', '鮭魚最棒了！', 5, '海鮮系列是本喵的最愛！每次開罐都衝過來！', NOW(), 'system', NOW(), 'system');
```

**Step 4: Commit**

```bash
git add -A
git commit -m "db: 重建 Flyway migration，新增 flavor/cat_profile/成分 tag"
```

---

## Task 3: 刪除全部舊 Java 原始碼

在重寫之前，先把舊的 `com.example.catfoodv1` package 下所有 Java 檔案刪除（測試檔也刪），確保新程式碼沒有對舊結構的隱性依賴。

**Files:**
- Delete: `src/main/java/com/example/catfoodv1/` (整個目錄)
- Delete: `src/test/java/com/example/catfoodv1/` (整個目錄)

**Step 1: 刪除舊原始碼**

刪除 `src/main/java/com/example/catfoodv1/` 和 `src/test/java/com/example/catfoodv1/` 下所有內容。

**Step 2: 建立新的根 package 目錄**

建立 `src/main/java/com/example/catfood/` 和 `src/test/java/com/example/catfood/`。

注意：package name 從 `catfoodv1` 改為 `catfood`（移除 v1）。

**Step 3: 建立 Spring Boot 啟動類**

Create: `src/main/java/com/example/catfood/CatFoodApplication.java`

```java
package com.example.catfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatFoodApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatFoodApplication.class, args);
    }
}
```

**Step 4: Commit**

```bash
git add -A
git commit -m "refactor: 移除所有舊原始碼，建立新 package 結構"
```

---

## Task 4: Domain 層 — Common（Auditable、Type、Tag）

**Files:**
- Create: `src/main/java/com/example/catfood/domain/common/entity/Auditable.java`
- Create: `src/main/java/com/example/catfood/domain/common/type/ProductType.java`
- Create: `src/main/java/com/example/catfood/domain/common/type/PackageUnit.java`
- Create: `src/main/java/com/example/catfood/domain/common/entity/Tag.java`
- Create: `src/main/java/com/example/catfood/domain/common/repository/TagRepository.java`

**Step 1: 建立 Auditable**

```java
package com.example.catfood.domain.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createDT;

    @LastModifiedDate
    @Column(name = "update_dt")
    private LocalDateTime updateDT;

    @CreatedBy
    @Column(name = "creator", updatable = false)
    private String creator;

    @LastModifiedBy
    @Column(name = "updater")
    private String updater;
}
```

**Step 2: 建立 Enum Types**

`ProductType.java`:
```java
package com.example.catfood.domain.common.type;

public enum ProductType {
    WET_FOOD("罐頭"),
    KIBBLE("飼料"),
    SAND("貓砂");

    public final String text;

    ProductType(String text) {
        this.text = text;
    }
}
```

`PackageUnit.java`:
```java
package com.example.catfood.domain.common.type;

public enum PackageUnit {
    BAG("袋"),
    CAN("罐"),
    BOX("箱");

    public final String text;

    PackageUnit(String text) {
        this.text = text;
    }
}
```

注意：移除 `WeightUnit`，目前沒有使用場景。

**Step 3: 建立 Tag Entity + Repository**

```java
package com.example.catfood.domain.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "tagCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "tag_code", unique = true, nullable = false)
    private String tagCode;

    @NotNull
    @Column(name = "tag_name", nullable = false)
    private String tagName;
}
```

```java
package com.example.catfood.domain.common.repository;

import com.example.catfood.domain.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
```

**Step 4: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 common 層 — Auditable, Type, Tag"
```

---

## Task 5: Domain 層 — Business（Brand、Company、Store）

**Files:**
- Create: `src/main/java/com/example/catfood/domain/business/entity/Company.java`
- Create: `src/main/java/com/example/catfood/domain/business/entity/Brand.java`
- Create: `src/main/java/com/example/catfood/domain/business/entity/Store.java`
- Create: `src/main/java/com/example/catfood/domain/business/repository/CompanyRepository.java`
- Create: `src/main/java/com/example/catfood/domain/business/repository/BrandRepository.java`
- Create: `src/main/java/com/example/catfood/domain/business/repository/StoreRepository.java`

**Step 1: 建立 Company**

```java
package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "companyCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "company_code", nullable = false, unique = true)
    private String companyCode;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;
}
```

**Step 2: 建立 Brand（company_id nullable）**

```java
package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "brandCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brand")
public class Brand extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "brand_code", nullable = false, unique = true)
    private String brandCode;

    @NotNull
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "company_id")
    private UUID companyId;
}
```

**Step 3: 建立 Store**

```java
package com.example.catfood.domain.business.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "storeCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store")
public class Store extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "store_code", nullable = false, unique = true)
    private String storeCode;

    @NotNull
    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "website_url")
    private String websiteUrl;
}
```

**Step 4: 建立 Repositories**

```java
package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
```

```java
package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
```

```java
package com.example.catfood.domain.business.repository;

import com.example.catfood.domain.business.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
```

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 business 層 — Brand, Company, Store"
```

---

## Task 6: Domain 層 — Account Aggregate（Account、CatProfile、Role）

**Files:**
- Create: `src/main/java/com/example/catfood/domain/account/entity/Role.java`
- Create: `src/main/java/com/example/catfood/domain/account/entity/Account.java`
- Create: `src/main/java/com/example/catfood/domain/account/entity/CatProfile.java`
- Create: `src/main/java/com/example/catfood/domain/account/repository/AccountRepository.java`

**Step 1: 建立 Role**

```java
package com.example.catfood.domain.account.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "roleCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "role_code", unique = true, nullable = false)
    private String roleCode;

    @NotNull
    @Column(name = "role_name", nullable = false)
    private String roleName;
}
```

**Step 2: 建立 CatProfile**

```java
package com.example.catfood.domain.account.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cat_profile")
public class CatProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "cat_name", nullable = false)
    private String catName;
}
```

**Step 3: 建立 Account（Aggregate Root）**

```java
package com.example.catfood.domain.account.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@EqualsAndHashCode(of = "accountCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "account_code", unique = true, nullable = false)
    private String accountCode;

    @NotNull
    @Column(nullable = false)
    private String email;

    @NotNull
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @NotNull
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private List<CatProfile> catProfiles = new ArrayList<>();

    public void addCatProfile(CatProfile catProfile) {
        this.catProfiles.add(catProfile);
    }
}
```

**Step 4: 建立 AccountRepository**

```java
package com.example.catfood.domain.account.repository;

import com.example.catfood.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountCode(String accountCode);
}
```

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 account 層 — Account, CatProfile, Role"
```

---

## Task 7: Domain 層 — Product Aggregate（Product、Flavor、Variant、PriceHistory）

**Files:**
- Create: `src/main/java/com/example/catfood/domain/product/entity/PriceHistory.java`
- Create: `src/main/java/com/example/catfood/domain/product/entity/Variant.java`
- Create: `src/main/java/com/example/catfood/domain/product/entity/Flavor.java`
- Create: `src/main/java/com/example/catfood/domain/product/entity/Product.java`
- Create: `src/main/java/com/example/catfood/domain/product/repository/ProductRepository.java`

**Step 1: 建立 PriceHistory（跨 Aggregate 引用用 ID）**

```java
package com.example.catfood.domain.product.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price_history")
public class PriceHistory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
```

**Step 2: 建立 Variant**

```java
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
```

**Step 3: 建立 Flavor**

```java
package com.example.catfood.domain.product.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flavor")
public class Flavor extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "flavor_name", nullable = false)
    private String flavorName;

    @Column(name = "ingredients", columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "protein_percentage", precision = 5, scale = 2)
    private BigDecimal proteinPercentage;

    @Column(name = "fat_percentage", precision = 5, scale = 2)
    private BigDecimal fatPercentage;

    @Column(name = "moisture_percentage", precision = 5, scale = 2)
    private BigDecimal moisturePercentage;

    @Column(name = "carbs_percentage", precision = 5, scale = 2)
    private BigDecimal carbsPercentage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "flavor_id")
    private List<Variant> variants = new ArrayList<>();

    public void addVariant(Variant variant) {
        this.variants.add(variant);
    }
}
```

**Step 4: 建立 Product（Aggregate Root）**

```java
package com.example.catfood.domain.product.entity;

import com.example.catfood.domain.common.entity.Auditable;
import com.example.catfood.domain.common.entity.Tag;
import com.example.catfood.domain.common.type.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(of = "productCode", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @NotNull
    @Column(name = "brand_id", nullable = false)
    private UUID brandId;

    @ManyToMany
    @JoinTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Flavor> flavors = new ArrayList<>();

    public void addFlavor(Flavor flavor) {
        this.flavors.add(flavor);
    }
}
```

**Step 5: 建立 ProductRepository（含 JOIN FETCH query）**

```java
package com.example.catfood.domain.product.repository;

import com.example.catfood.domain.common.type.ProductType;
import com.example.catfood.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.flavors f " +
           "LEFT JOIN FETCH f.variants v " +
           "LEFT JOIN FETCH v.priceHistories " +
           "WHERE p.productType = :type")
    List<Product> findAllWithDetailsByType(@Param("type") ProductType type);

    @Query("SELECT DISTINCT p FROM Product p " +
           "LEFT JOIN FETCH p.flavors f " +
           "LEFT JOIN FETCH f.variants v " +
           "LEFT JOIN FETCH v.priceHistories")
    List<Product> findAllWithDetails();
}
```

**Step 6: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 product 層 — Product, Flavor, Variant, PriceHistory"
```

---

## Task 8: Domain 層 — Review Aggregate

**Files:**
- Create: `src/main/java/com/example/catfood/domain/review/entity/ProductReview.java`
- Create: `src/main/java/com/example/catfood/domain/review/repository/ProductReviewRepository.java`

**Step 1: 建立 ProductReview（跨 Aggregate 引用用 ID）**

```java
package com.example.catfood.domain.review.entity;

import com.example.catfood.domain.common.entity.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_review",
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "cat_profile_id"}))
public class ProductReview extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @NotNull
    @Column(name = "cat_profile_id", nullable = false)
    private UUID catProfileId;

    @Column
    private Integer rating;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
```

**Step 2: 建立 ProductReviewRepository**

```java
package com.example.catfood.domain.review.repository;

import com.example.catfood.domain.review.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {
    List<ProductReview> findByProductId(UUID productId);
}
```

**Step 3: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 review 層 — ProductReview"
```

---

## Task 9: Domain Service — PriceDomainService + 單元測試

**Files:**
- Create: `src/main/java/com/example/catfood/domain/product/service/PriceDomainService.java`
- Create: `src/test/java/com/example/catfood/domain/product/service/PriceDomainServiceTest.java`

**Step 1: 寫 PriceDomainService 的測試**

```java
package com.example.catfood.domain.product.service;

import com.example.catfood.domain.product.entity.PriceHistory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PriceDomainServiceTest {

    private final PriceDomainService service = new PriceDomainService();

    @Test
    void findLowestPrice_returnsLowest() {
        PriceHistory low = createPrice(new BigDecimal("43.00"));
        PriceHistory mid = createPrice(new BigDecimal("45.00"));
        PriceHistory high = createPrice(new BigDecimal("46.00"));

        Optional<PriceHistory> result = service.findLowestPrice(List.of(high, low, mid));

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("43.00"), result.get().getPrice());
    }

    @Test
    void findLowestPrice_emptyList_returnsEmpty() {
        Optional<PriceHistory> result = service.findLowestPrice(Collections.emptyList());
        assertTrue(result.isEmpty());
    }

    @Test
    void calculatePerHundredGrams_normalCase() {
        BigDecimal result = service.calculatePerHundredGrams(new BigDecimal("43.00"), 85);
        assertEquals(new BigDecimal("50.59"), result);
    }

    @Test
    void calculatePerHundredGrams_boxCase() {
        // 24罐箱 900元, 每罐85g, 總重 85*24=2040g
        BigDecimal result = service.calculatePerHundredGrams(new BigDecimal("900.00"), 2040);
        assertEquals(new BigDecimal("44.12"), result);
    }

    private PriceHistory createPrice(BigDecimal price) {
        PriceHistory ph = new PriceHistory();
        ph.setId(UUID.randomUUID());
        ph.setStoreId(UUID.randomUUID());
        ph.setPrice(price);
        return ph;
    }
}
```

**Step 2: 執行測試確認失敗**

Run: `./mvnw test -pl . -Dtest=PriceDomainServiceTest -Dsurefire.failIfNoSpecifiedTests=false`
Expected: FAIL (class not found)

**Step 3: 實作 PriceDomainService**

```java
package com.example.catfood.domain.product.service;

import com.example.catfood.domain.product.entity.PriceHistory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceDomainService {

    public Optional<PriceHistory> findLowestPrice(List<PriceHistory> histories) {
        return histories.stream()
                .min(Comparator.comparing(PriceHistory::getPrice));
    }

    public BigDecimal calculatePerHundredGrams(BigDecimal price, int weightGrams) {
        return price.multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(weightGrams), 2, RoundingMode.HALF_UP);
    }
}
```

**Step 4: 執行測試確認通過**

Run: `./mvnw test -pl . -Dtest=PriceDomainServiceTest`
Expected: PASS

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(domain): 建立 PriceDomainService + 單元測試"
```

---

## Task 10: Infrastructure 層 — Config

**Files:**
- Create: `src/main/java/com/example/catfood/infrastructure/config/SecurityConfig.java`
- Create: `src/main/java/com/example/catfood/infrastructure/config/JpaAuditingConfiguration.java`
- Create: `src/main/java/com/example/catfood/infrastructure/aspect/LoggingAspect.java`
- Create: `src/main/java/com/example/catfood/infrastructure/aspect/NoLogging.java`
- Create: `src/main/java/com/example/catfood/AppShell.java`

**Step 1: 建立 SecurityConfig（已移除 H2 相關設定）**

```java
package com.example.catfood.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/secure/**", "/user/settings/**", "/admin/**").authenticated()
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("accountCode")
                .permitAll()
        );

        http.logout(LogoutConfigurer::permitAll);

        List<String> protectedPaths = List.of("/api/secure/", "/user/settings", "/admin/");
        http.csrf(csrf -> csrf.ignoringRequestMatchers(request -> {
            String path = request.getRequestURI();
            boolean isProtected = protectedPaths.stream().anyMatch(path::startsWith);
            return !isProtected;
        }));

        return http.build();
    }
}
```

**Step 2: 建立 JpaAuditingConfiguration**

```java
package com.example.catfood.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableCaching
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                return Optional.of("System");
            }
            return Optional.of(auth.getName());
        };
    }
}
```

**Step 3: 建立 LoggingAspect + NoLogging**

從舊版複製邏輯，只改 package path。參考舊檔 `src/main/java/com/example/catfoodv1/aspect/LoggingAspect.java` 和 `NoLogging.java`，更新 pointcut 中的 package 路徑為 `com.example.catfood`。

**Step 4: 建立 AppShell（Vaadin）**

```java
package com.example.catfood;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

@Theme("catfood")
public class AppShell implements AppShellConfigurator {
}
```

注意：如果原本沒用自定 theme，可以移除 `@Theme` annotation。

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(infra): 建立 SecurityConfig, JPA Auditing, AOP"
```

---

## Task 11: Application 層 — DTO + Service

**Files:**
- Create: `src/main/java/com/example/catfood/application/dto/WetFoodViewDto.java`
- Create: `src/main/java/com/example/catfood/application/dto/ProductCreateCommand.java`
- Create: `src/main/java/com/example/catfood/application/dto/PriceAddCommand.java`
- Create: `src/main/java/com/example/catfood/application/dto/CommonDto.java`
- Create: `src/main/java/com/example/catfood/application/ProductApplicationService.java`
- Create: `src/main/java/com/example/catfood/application/CommonApplicationService.java`
- Create: `src/main/java/com/example/catfood/application/auth/UserDetailsServiceImpl.java`

**Step 1: 建立 DTO**

`WetFoodViewDto.java`:
```java
package com.example.catfood.application.dto;

import com.example.catfood.domain.common.type.PackageUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WetFoodViewDto {
    private UUID productId;
    private UUID variantId;
    private UUID priceId;
    private String brandName;
    private String displayName;  // 產品名 - 口味名
    private String storeName;
    private PackageUnit unit;
    private BigDecimal price;
    private BigDecimal pricePer;
    private LocalDateTime updateDT;
    private List<WetFoodViewDto> details = new ArrayList<>();
}
```

`ProductCreateCommand.java`:
```java
package com.example.catfood.application.dto;

import com.example.catfood.domain.common.type.PackageUnit;
import com.example.catfood.domain.common.type.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductCreateCommand {
    private String productCode;
    private String productName;
    private ProductType productType;
    private UUID brandId;
    private List<UUID> tagIds;
    // Flavor info
    private String flavorName;
    private String ingredients;
    private BigDecimal proteinPercentage;
    private BigDecimal fatPercentage;
    private BigDecimal moisturePercentage;
    private BigDecimal carbsPercentage;
    // Variant info
    private String sku;
    private Integer packageWeightGrams;
    private Integer packSize;
    private PackageUnit unitOfMeasure;
    // Price info
    private UUID storeId;
    private BigDecimal price;
}
```

`PriceAddCommand.java`:
```java
package com.example.catfood.application.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PriceAddCommand {
    private UUID productId;
    private UUID variantId;
    private UUID storeId;
    private BigDecimal price;
}
```

`CommonDto.java`:
```java
package com.example.catfood.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommonDto {
    private UUID id;
    private String code;
    private String name;
}
```

**Step 2: 建立 ProductApplicationService**

```java
package com.example.catfood.application;

import com.example.catfood.application.dto.PriceAddCommand;
import com.example.catfood.application.dto.WetFoodViewDto;
import com.example.catfood.domain.business.entity.Brand;
import com.example.catfood.domain.business.entity.Store;
import com.example.catfood.domain.business.repository.BrandRepository;
import com.example.catfood.domain.business.repository.StoreRepository;
import com.example.catfood.domain.common.type.ProductType;
import com.example.catfood.domain.product.entity.*;
import com.example.catfood.domain.product.repository.ProductRepository;
import com.example.catfood.domain.product.service.PriceDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductApplicationService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final PriceDomainService priceDomainService;

    @Transactional(readOnly = true)
    @Cacheable("wetFoodList")
    public List<WetFoodViewDto> getWetFoodList() {
        List<Product> products = productRepository.findAllWithDetailsByType(ProductType.WET_FOOD);

        // 預載所有需要的 Brand 和 Store（批次查，避免 N+1）
        Set<UUID> brandIds = products.stream().map(Product::getBrandId).collect(Collectors.toSet());
        Map<UUID, String> brandNameMap = brandRepository.findAllById(brandIds).stream()
                .collect(Collectors.toMap(Brand::getId, Brand::getBrandName));

        Set<UUID> storeIds = products.stream()
                .flatMap(p -> p.getFlavors().stream())
                .flatMap(f -> f.getVariants().stream())
                .flatMap(v -> v.getPriceHistories().stream())
                .map(PriceHistory::getStoreId)
                .collect(Collectors.toSet());
        Map<UUID, String> storeNameMap = storeRepository.findAllById(storeIds).stream()
                .collect(Collectors.toMap(Store::getId, Store::getStoreName));

        return products.stream()
                .flatMap(product -> product.getFlavors().stream()
                        .flatMap(flavor -> flavor.getVariants().stream()
                                .map(variant -> buildDto(product, flavor, variant, brandNameMap, storeNameMap))))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(WetFoodViewDto::getBrandName)
                        .thenComparing(WetFoodViewDto::getDisplayName)
                        .thenComparing(WetFoodViewDto::getPrice))
                .collect(Collectors.toList());
    }

    private WetFoodViewDto buildDto(Product product, Flavor flavor, Variant variant,
                                     Map<UUID, String> brandNameMap, Map<UUID, String> storeNameMap) {
        Optional<PriceHistory> lowestOpt = priceDomainService.findLowestPrice(variant.getPriceHistories());
        if (lowestOpt.isEmpty()) return null;

        PriceHistory lowest = lowestOpt.get();
        int totalWeight = variant.getPackageWeightGrams() * (variant.getPackSize() != null ? variant.getPackSize() : 1);

        WetFoodViewDto dto = new WetFoodViewDto();
        dto.setProductId(product.getId());
        dto.setVariantId(variant.getId());
        dto.setPriceId(lowest.getId());
        dto.setBrandName(brandNameMap.getOrDefault(product.getBrandId(), ""));
        dto.setDisplayName(product.getProductName() + " - " + flavor.getFlavorName());
        dto.setStoreName(storeNameMap.getOrDefault(lowest.getStoreId(), ""));
        dto.setUnit(variant.getUnitOfMeasure());
        dto.setPrice(lowest.getPrice());
        dto.setPricePer(priceDomainService.calculatePerHundredGrams(lowest.getPrice(), totalWeight));
        dto.setUpdateDT(lowest.getUpdateDT());

        // 其他價格作為明細
        dto.setDetails(variant.getPriceHistories().stream()
                .filter(ph -> !ph.getId().equals(lowest.getId()))
                .sorted(Comparator.comparing(PriceHistory::getPrice))
                .map(ph -> {
                    WetFoodViewDto detail = new WetFoodViewDto();
                    detail.setProductId(product.getId());
                    detail.setVariantId(variant.getId());
                    detail.setPriceId(ph.getId());
                    detail.setBrandName(dto.getBrandName());
                    detail.setDisplayName(dto.getDisplayName());
                    detail.setStoreName(storeNameMap.getOrDefault(ph.getStoreId(), ""));
                    detail.setUnit(variant.getUnitOfMeasure());
                    detail.setPrice(ph.getPrice());
                    detail.setPricePer(priceDomainService.calculatePerHundredGrams(ph.getPrice(), totalWeight));
                    detail.setUpdateDT(ph.getUpdateDT());
                    return detail;
                })
                .toList());

        return dto;
    }

    @Transactional
    @CacheEvict(value = "wetFoodList", allEntries = true)
    public void addPriceToVariant(PriceAddCommand cmd) {
        Product product = productRepository.findById(cmd.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.getFlavors().stream()
                .flatMap(f -> f.getVariants().stream())
                .filter(v -> v.getId().equals(cmd.getVariantId()))
                .findFirst()
                .ifPresent(variant -> {
                    PriceHistory ph = new PriceHistory();
                    ph.setStoreId(cmd.getStoreId());
                    ph.setPrice(cmd.getPrice());
                    variant.addPriceHistory(ph);
                });

        productRepository.save(product);
    }
}
```

**Step 3: 建立 CommonApplicationService**

```java
package com.example.catfood.application;

import com.example.catfood.application.dto.CommonDto;
import com.example.catfood.domain.business.repository.BrandRepository;
import com.example.catfood.domain.business.repository.CompanyRepository;
import com.example.catfood.domain.business.repository.StoreRepository;
import com.example.catfood.domain.common.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonApplicationService {

    private final BrandRepository brandRepository;
    private final CompanyRepository companyRepository;
    private final StoreRepository storeRepository;
    private final TagRepository tagRepository;

    public Set<CommonDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(b -> new CommonDto(b.getId(), b.getBrandCode(), b.getBrandName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(c -> new CommonDto(c.getId(), c.getCompanyCode(), c.getCompanyName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(s -> new CommonDto(s.getId(), s.getStoreCode(), s.getStoreName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> new CommonDto(t.getId(), t.getTagCode(), t.getTagName()))
                .collect(Collectors.toSet());
    }
}
```

**Step 4: 建立 UserDetailsServiceImpl**

```java
package com.example.catfood.application.auth;

import com.example.catfood.domain.account.entity.Account;
import com.example.catfood.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountCode) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountCode(accountCode)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + accountCode));

        return new User(
                account.getAccountCode(),
                account.getPassword(),
                account.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                        .toList()
        );
    }
}
```

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(app): 建立 Application Service + DTO"
```

---

## Task 12: View 層 — Vaadin UI 調整

**Files:**
- Create: `src/main/java/com/example/catfood/view/MainLayout.java`
- Create: `src/main/java/com/example/catfood/view/LoginView.java`
- Create: `src/main/java/com/example/catfood/view/wet/WetFoodView.java`

**Step 1: 建立 MainLayout**

從舊版複製邏輯，更新 import path 為 `com.example.catfood`。

**Step 2: 建立 LoginView**

從舊版複製邏輯，更新 import path。

**Step 3: 建立 WetFoodView（使用新的 Application Service）**

```java
package com.example.catfood.view.wet;

import com.example.catfood.application.ProductApplicationService;
import com.example.catfood.application.dto.WetFoodViewDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@PageTitle("咪貓罐頭")
@AnonymousAllowed
@Route(value = "")
@RouteAlias(value = "wet-food")
public class WetFoodView extends VerticalLayout {

    private final ProductApplicationService productApplicationService;
    private final TreeGrid<WetFoodViewDto> grid = new TreeGrid<>(WetFoodViewDto.class, false);

    public WetFoodView(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
        initGrid();
        search();
        setSizeFull();
    }

    private void search() {
        List<WetFoodViewDto> list = productApplicationService.getWetFoodList();
        grid.setItems(list, WetFoodViewDto::getDetails);
    }

    private void initGrid() {
        grid.setAllRowsVisible(true);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addHierarchyColumn(WetFoodViewDto::getBrandName).setHeader("品牌");
        grid.addColumn(WetFoodViewDto::getDisplayName).setHeader("品名");
        grid.addColumn(WetFoodViewDto::getStoreName).setHeader("販售處");
        grid.addColumn(WetFoodViewDto::getUnit).setHeader("單位")
                .setRenderer(new TextRenderer<>(dto -> dto.getUnit() != null ? dto.getUnit().text : ""));
        grid.addColumn(WetFoodViewDto::getPrice).setHeader("價格");
        grid.addColumn(WetFoodViewDto::getPricePer).setHeader("百克價格");
        grid.addColumn(WetFoodViewDto::getUpdateDT).setHeader("最後更新")
                .setRenderer(new LocalDateTimeRenderer<>(WetFoodViewDto::getUpdateDT));
        grid.getColumns().forEach(c -> c.setResizable(true).setAutoWidth(true));
        grid.setWidthFull();
        add(grid);
    }
}
```

注意：CreateDialog 和其他 View component 的重建可以在後續的功能開發中逐步完成，此 Task 先確保主要列表頁能正常運作。KibbleView 和 SandView 同理，先建立空殼。

**Step 4: 建立 KibbleView 和 SandView 空殼**

```java
package com.example.catfood.view.kibble;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("飼料")
@AnonymousAllowed
@Route(value = "kibble")
public class KibbleView extends VerticalLayout {
}
```

```java
package com.example.catfood.view.sand;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("貓砂")
@AnonymousAllowed
@Route(value = "sand")
public class SandView extends VerticalLayout {
}
```

**Step 5: Commit**

```bash
git add -A
git commit -m "feat(view): 建立 Vaadin View 層"
```

---

## Task 13: 整合測試 — Testcontainers + Flyway

**Files:**
- Create: `src/test/java/com/example/catfood/CatFoodApplicationTests.java`
- Create: `src/test/java/com/example/catfood/domain/product/repository/ProductRepositoryIntegrationTest.java`

**Step 1: 建立基本啟動測試**

```java
package com.example.catfood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class CatFoodApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration/postgresql");
    }

    @Test
    void contextLoads() {
    }
}
```

**Step 2: 建立 ProductRepository 整合測試**

```java
package com.example.catfood.domain.product.repository;

import com.example.catfood.domain.common.type.ProductType;
import com.example.catfood.domain.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ProductRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.locations", () -> "classpath:db/migration/postgresql");
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllWithDetailsByType_loadsFullAggregate() {
        List<Product> products = productRepository.findAllWithDetailsByType(ProductType.WET_FOOD);

        assertFalse(products.isEmpty());
        Product first = products.get(0);
        assertNotNull(first.getProductCode());
        assertFalse(first.getFlavors().isEmpty());
        assertFalse(first.getFlavors().get(0).getVariants().isEmpty());
    }
}
```

**Step 3: 執行全部測試**

Run: `./mvnw test`
Expected: ALL PASS

**Step 4: Commit**

```bash
git add -A
git commit -m "test: 新增整合測試 — Context Load + ProductRepository"
```

---

## Task 14: 最終清理

**Files:**
- Modify: `pom.xml` (更新 artifactId 的 main class 如有需要)
- Verify: `Dockerfile` (確保 entrypoint 指向正確的 class)

**Step 1: 確認 application.properties 中的 main class 設定正確**

如果有指定 `spring.main.class` 或 Dockerfile 中 hardcode 了 class name，更新為 `com.example.catfood.CatFoodApplication`。

**Step 2: 執行完整建置**

Run: `./mvnw clean package -DskipTests`
Expected: BUILD SUCCESS

**Step 3: 執行完整測試**

Run: `./mvnw test`
Expected: ALL PASS

**Step 4: Commit**

```bash
git add -A
git commit -m "chore: 最終清理，確認建置與測試通過"
```

---

## 總覽

| Task | 內容 | 預估 commit |
|------|------|-------------|
| 1 | 依賴清理與配置檔整理 | `chore: 移除 H2/MySQL 依賴` |
| 2 | 新的 Flyway Migration | `db: 重建 Flyway migration` |
| 3 | 刪除舊原始碼 + 建新 package | `refactor: 移除所有舊原始碼` |
| 4 | Domain Common (Auditable, Type, Tag) | `feat(domain): common 層` |
| 5 | Domain Business (Brand, Company, Store) | `feat(domain): business 層` |
| 6 | Domain Account (Account, CatProfile, Role) | `feat(domain): account 層` |
| 7 | Domain Product (Product, Flavor, Variant, PriceHistory) | `feat(domain): product 層` |
| 8 | Domain Review (ProductReview) | `feat(domain): review 層` |
| 9 | PriceDomainService + 單元測試 | `feat(domain): PriceDomainService` |
| 10 | Infrastructure Config | `feat(infra): Config` |
| 11 | Application Service + DTO | `feat(app): Application Service` |
| 12 | Vaadin View 層 | `feat(view): View 層` |
| 13 | 整合測試 | `test: 整合測試` |
| 14 | 最終清理 | `chore: 最終清理` |
