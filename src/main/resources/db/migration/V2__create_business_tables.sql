-- V2: Create business tables for Company, Brand, Store

CREATE TABLE company (
    id UUID PRIMARY KEY,
    company_code VARCHAR(255) NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

CREATE TABLE brand (
    id UUID PRIMARY KEY,
    brand_code VARCHAR(255) NOT NULL UNIQUE,
    brand_name VARCHAR(255) NOT NULL,
    company_id UUID NOT NULL,
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255),
    CONSTRAINT fk_brand_company FOREIGN KEY (company_id) REFERENCES company(id)
);

CREATE TABLE store (
    id UUID PRIMARY KEY,
    store_code VARCHAR(255) NOT NULL UNIQUE,
    store_name VARCHAR(255) NOT NULL,
    website_url VARCHAR(255),
    create_dt TIMESTAMP,
    creator VARCHAR(255),
    update_dt TIMESTAMP,
    updater VARCHAR(255)
);

