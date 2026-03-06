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
