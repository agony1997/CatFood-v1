-- V4: Insert initial data (PostgreSQL compatible)
-- NOTE: This script uses gen_random_uuid() which requires the pgcrypto extension.

-- Roles (Assuming IDs 1 and 2 are generated for ROLE_USER and ROLE_ADMIN)
INSERT INTO role (id, role_code, role_name, create_dt, creator, update_dt, updater) VALUES
(1, 'ROLE_USER', '一般使用者', NOW(), 'system', NOW(), 'system'),
(2, 'ROLE_ADMIN', '管理員', NOW(), 'system', NOW(), 'system');

-- Accounts
-- Password for '123' is '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
INSERT INTO account (id, account_code, email, display_name, password, create_dt, creator, update_dt, updater) VALUES
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', 'admin', 'admin@example.com', 'Kuma', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system'),
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', 'normal', 'normal@example.com', 'cat', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system');

-- User Roles
INSERT INTO user_roles (account_id, role_id) VALUES
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', 1),
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', 2),
('a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', 1);

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
('50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 'OFFICEAL', '官網', NOW(), 'system', NOW(), 'system');

-- Tags
INSERT INTO tag (id, tag_code, tag_name, create_dt, creator, update_dt, updater) VALUES
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001', 'GRAIN_FREE', '無穀', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a002', 'KITTEN', '幼貓專用', NOW(), 'system', NOW(), 'system'),
('70a0a0a0-70a0-70a0-70a0-a0a0a0a0a003', 'HAIRBALL_CONTROL', '化毛', NOW(), 'system', NOW(), 'system');

-- Ingredients
INSERT INTO ingredient (id, ingredient_code, ingredient_name, create_dt, creator, update_dt, updater) VALUES
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 'CHICKEN', '雞肉', NOW(), 'system', NOW(), 'system'),
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a002', 'DEER', '鹿肉', NOW(), 'system', NOW(), 'system'),
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a003', 'TURKEY', '火雞肉', NOW(), 'system', NOW(), 'system'),
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a004', 'SALMON', '鮭魚', NOW(), 'system', NOW(), 'system'),
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a005', 'BEEF', '牛肉', NOW(), 'system', NOW(), 'system'),
('10a0a0a0-10a0-10a0-10a0-a0a0a0a0a006', 'TUNA', '鮪魚', NOW(), 'system', NOW(), 'system');

-- Company
INSERT INTO company (id, company_code, company_name, create_dt, creator, update_dt, updater) VALUES
('c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', 'SHEP', '牧羊人集團', NOW(), 'system', NOW(), 'system');

-- Brands
INSERT INTO brand (id, brand_code, brand_name, company_id, create_dt, creator, update_dt, updater) VALUES
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a001', 'DOGCAT', '汪喵星球', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'MONSTER', '怪獸部落', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a003', 'HEROMA', 'HeroMama', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system'),
('b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a004', 'LADY', '超凝小姐', 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001', NOW(), 'system', NOW(), 'system');

-- Products
INSERT INTO product (id, product_code, product_name, brand_id, product_type, create_dt, creator, update_dt, updater) VALUES
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'DOGCAT-001', '鮮肉罐', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a001', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'MONSTER-001', '怪獸海鮮系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', 'MONSTER-002', '怪獸陸地系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004', 'MONSTER-003', '怪獸野味系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a005', 'MONSTER-004', '怪獸特選系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006', 'HEROMA-001', 'HeroMama 鮮肉系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a003', 'WET_FOOD', NOW(), 'system', NOW(), 'system'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', 'HEROMA-002', 'HeroMama 海鮮系列', 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a003', 'WET_FOOD', NOW(), 'system', NOW(), 'system');

-- Product Tags
INSERT INTO product_tag (product_id, tag_id) VALUES
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a002'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a003'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a005', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001'),
('90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a002');

-- Product Variants
-- DogCat Products
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'DOGCAT-001-TURKEY-85G1C', '田園火雞', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'DOGCAT-001-TURKEY-85G24C', '田園火雞', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'DOGCAT-001-BEEF-85G1C', '草飼牛肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Sea Series (怪獸海鮮系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'MONSTER-001-SALMON-85G1C', '怪獸鮭魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'MONSTER-001-SALMON-85G24C', '怪獸鮭魚', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'MONSTER-001-TUNA-85G1C', '怪獸鮪魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Land Series (怪獸陸地系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', 'MONSTER-002-CHICKEN-85G1C', '怪獸雞肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', 'MONSTER-002-BEEF-85G1C', '怪獸牛肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Wild Series (怪獸野味系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004', 'MONSTER-003-DEER-85G1C', '怪獸鹿肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004', 'MONSTER-003-LAMB-85G1C', '怪獸羊肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Premium Series (怪獸特選系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a005', 'MONSTER-004-DUCK-85G1C', '怪獸鴨肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- HeroMama Product Variants
-- HeroMama 鮮肉系列
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006', 'HEROMA-001-TURKEY-85G1C', 'Hero 火雞', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006', 'HEROMA-001-CHICKEN-85G1C', 'Hero 雞肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- HeroMama 海鮮系列
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', 'HEROMA-002-SALMON-85G1C', 'Hero 鮭魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', 'HEROMA-002-TUNA-85G1C', 'Hero 鮪魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Variant Ingredient Mappings
-- DogCat Products
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a003', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a003', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a005', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2);

-- Monster Product Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a004', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a004', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a006', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a005', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a002', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a005', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 1);

-- HeroMama Product Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a003', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a004', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a006', 1),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001', 2);

-- Product Details
-- DogCat Products
INSERT INTO product_detail (product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '火雞肉、雞心肝、雞蛋黃...', 15.1, 6.0, 75.9, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '牛肉、雞肉、雞心肝、雞蛋黃...', 15.8, 6.6, 74.6, NOW(), 'system', NOW(), 'system');

-- Monster Product Details
INSERT INTO product_detail (product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '鮭魚、雞心肝、雞蛋黃...', 15.0, 6.0, 75.0, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '鮭魚、雞心肝、雞蛋黃...', 15.0, 6.0, 75.0, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '鮪魚、雞心肝、雞蛋黃...', 15.2, 6.2, 74.8, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '雞肉、雞心肝、雞蛋黃...', 15.5, 6.5, 74.5, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '牛肉、雞心肝、雞蛋黃...', 16.0, 7.0, 74.0, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '鹿肉、雞心肝、雞蛋黃...', 16.5, 6.8, 73.5, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '羊肉、雞心肝、雞蛋黃...', 16.2, 7.2, 73.8, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '鴨肉、雞心肝、雞蛋黃...', 15.8, 8.0, 74.2, NOW(), 'system', NOW(), 'system');

-- HeroMama Product Details
INSERT INTO product_detail (product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', 'Hero火雞肉、雞心肝、雞蛋黃...', 15.3, 6.2, 75.5, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', 'Hero雞肉、雞心肝、雞蛋黃...', 15.6, 6.4, 74.8, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', 'Hero鮭魚、雞心肝、雞蛋黃...', 15.2, 6.1, 75.2, NOW(), 'system', NOW(), 'system'),
('d0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', 'Hero鮪魚、雞心肝、雞蛋黃...', 15.4, 6.3, 74.9, NOW(), 'system', NOW(), 'system');

-- Product Price History
-- DogCat Product Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 46.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 45.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 43.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 1100.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 900.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 1000.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 46.0, NOW(), 'system', NOW(), 'system');

-- Monster Sea Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 50.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 48.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 45.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 1200.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 1000.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 1100.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 55.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 53.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 50.0, NOW(), 'system', NOW(), 'system');

-- Monster Land Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 48.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 46.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 43.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 52.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 50.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 48.0, NOW(), 'system', NOW(), 'system');

-- Monster Wild Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 58.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 55.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 52.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 60.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 57.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 54.0, NOW(), 'system', NOW(), 'system');

-- Monster Premium Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 62.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 59.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 56.0, NOW(), 'system', NOW(), 'system');

-- HeroMama Product Prices
-- HeroMama 鮮肉系列 Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 52.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 50.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 47.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 49.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 47.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 44.0, NOW(), 'system', NOW(), 'system');

-- HeroMama 海鮮系列 Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 56.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 54.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 51.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010', 58.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002', 56.0, NOW(), 'system', NOW(), 'system'),
(gen_random_uuid(), 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015', '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005', 53.0, NOW(), 'system', NOW(), 'system');

-- Add sample product reviews for demonstration
INSERT INTO product_review (id, product_id, account_id, title, rating, comment, create_dt, creator, update_dt, updater) VALUES
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', '鮭魚罐頭評測', 5, '我家貓咪超愛這個鮭魚罐頭！適口性很好，而且成分單純，沒有不必要的添加物。', NOW(), 'admin', NOW(), 'admin'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', '海鮮系列不錯', 4, '怪獸部落的海鮮系列品質穩定，貓咪吃得很開心，價格也合理。', NOW(), 'normal', NOW(), 'normal'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', '陸地系列評測', 4, '雞肉和牛肉口味都不錯，不過我家貓比較偏愛海鮮系列。', NOW(), 'admin', NOW(), 'admin'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', '野味系列高端', 5, '鹿肉和羊肉都是高蛋白，適合活動量大的貓咪。品質很棒！', NOW(), 'normal', NOW(), 'normal'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', '汪喵星球好評', 4, '火雞肉罐頭很香，貓咪一聞到就跑過來了。營養成分也很均衡。', NOW(), 'admin', NOW(), 'admin'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001', 'HeroMama 品質優良', 5, 'HeroMama 的鮮肉系列真的很棒！貓咪第一次吃就愛上了，而且營養豐富。', NOW(), 'admin', NOW(), 'admin'),
(gen_random_uuid(), '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007', 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002', 'Hero海鮮讚', 4, 'HeroMama 海鮮系列的鮭魚罐很新鮮，貓咪吃得津津有味，推薦！', NOW(), 'normal', NOW(), 'normal');
