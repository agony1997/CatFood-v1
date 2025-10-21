-- V3: Create product-related tables (MySQL specific)

CREATE TABLE ingredient (
    id CHAR(36) PRIMARY KEY,
    ingredient_code VARCHAR(20) NOT NULL UNIQUE,
    ingredient_name VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE tag (
    id CHAR(36) PRIMARY KEY,
    tag_code VARCHAR(255) NOT NULL UNIQUE,
    tag_name VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE product (
    id CHAR(36) PRIMARY KEY,
    product_code VARCHAR(255) NOT NULL UNIQUE,
    product_name VARCHAR(255) NOT NULL,
    brand_id CHAR(36) NOT NULL,
    product_type VARCHAR(31) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE TABLE product_variant (
    id CHAR(36) PRIMARY KEY,
    product_id CHAR(36) NOT NULL,
    sku VARCHAR(255) NOT NULL UNIQUE,
    variant_name VARCHAR(255),
    package_weight_grams INTEGER,
    pack_size INTEGER,
    unit_of_measure VARCHAR(31),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_variant_product FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE product_detail (
    id CHAR(36) PRIMARY KEY,
    product_variant_id CHAR(36) NOT NULL UNIQUE,
    ingredients TEXT,
    protein_percentage DECIMAL(5,2),
    fat_percentage DECIMAL(5,2),
    moisture_percentage DECIMAL(5,2),
    carbs_percentage DECIMAL(5,2),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_detail_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant(id),
    CONSTRAINT uq_detail_variant UNIQUE (product_variant_id)
);

CREATE TABLE variant_ingredient_mapping (
    id CHAR(36) PRIMARY KEY,
    product_variant_id CHAR(36) NOT NULL,
    ingredient_id CHAR(36) NOT NULL,
    ingredient_order INTEGER,
    CONSTRAINT fk_vim_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant(id),
    CONSTRAINT fk_vim_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);

CREATE TABLE product_price_history (
    id CHAR(36) PRIMARY KEY,
    product_variant_id CHAR(36) NOT NULL,
    store_id CHAR(36),
    price INTEGER NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_price_variant FOREIGN KEY (product_variant_id) REFERENCES product_variant(id),
    CONSTRAINT fk_price_store FOREIGN KEY (store_id) REFERENCES store(id)
);
CREATE INDEX idx_price_history_query ON product_price_history(product_variant_id, store_id, create_dt);

CREATE TABLE product_tag (
    product_id CHAR(36) NOT NULL,
    tag_id CHAR(36) NOT NULL,
    PRIMARY KEY (product_id, tag_id),
    CONSTRAINT fk_pt_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_pt_tag FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE product_review (
    id CHAR(36) PRIMARY KEY,
    product_id CHAR(36) NOT NULL,
    account_id CHAR(36) NOT NULL,
    title VARCHAR(255),
    rating INTEGER,
    comment TEXT,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_review_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT chk_rating CHECK (rating >= 1 AND rating <= 5)
);
