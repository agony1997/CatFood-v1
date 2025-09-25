-- V4: Insert initial data from DataInitializer

-- Pre-defined UUIDs for entities to manage relationships
-- Roles are BIGINT auto-increment, so no UUID needed.
-- Accounts
SET @admin_user_id = 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a001';
SET @normal_user_id = 'a0a0a0a0-a0a0-a0a0-a0a0-a0a0a0a0a002';

-- Stores
SET @store_pc_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a001';
SET @store_momo_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a002';
SET @store_coup_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a003';
SET @store_shop_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a004';
SET @store_old_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a005';
SET @store_bone_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a006';
SET @store_li66_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a007';
SET @store_luck_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a008';
SET @store_park_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a009';
SET @store_off_id = '50a0a0a0-50a0-50a0-50a0-a0a0a0a0a010';

-- Tags
SET @tag_gf_id = '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a001';
SET @tag_kit_id = '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a002';
SET @tag_hair_id = '70a0a0a0-70a0-70a0-70a0-a0a0a0a0a003';

-- Ingredients
SET @ing_chicken_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a001';
SET @ing_deer_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a002';
SET @ing_turkey_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a003';
SET @ing_salmon_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a004';
SET @ing_beef_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a005';
SET @ing_tuna_id = '10a0a0a0-10a0-10a0-10a0-a0a0a0a0a006';

-- Company & Brands
SET @comp_shep_id = 'c0a0a0a0-c0a0-c0a0-c0a0-a0a0a0a0a001';
SET @brand_dogcat_id = 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a001';
SET @brand_monster_id = 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a002';
SET @brand_heroma_id = 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a003';
SET @brand_lady_id = 'b0a0a0a0-b0a0-b0a0-b0a0-a0a0a0a0a004';

-- Products
SET @prod_dogcat001_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a001';

-- Product Variants
SET @var_turkey_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001';
SET @var_turkey_box_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002';
SET @var_beef_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003';

-- Roles (Assuming IDs 1 and 2 are generated for ROLE_USER and ROLE_ADMIN)
INSERT INTO role (id, role_code, role_name, create_dt, creator, update_dt, updater) VALUES
(1, 'ROLE_USER', '一般使用者', NOW(), 'system', NOW(), 'system'),
(2, 'ROLE_ADMIN', '管理員', NOW(), 'system', NOW(), 'system');

-- Accounts
-- Password for '123' is '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
INSERT INTO account (id, account_code, email, display_name, password, create_dt, creator, update_dt, updater) VALUES
(@admin_user_id, 'admin', 'admin@example.com', 'Kuma', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system'),
(@normal_user_id, 'normal', 'normal@example.com', 'cat', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'system', NOW(), 'system');

-- User Roles
INSERT INTO user_roles (account_id, role_id) VALUES
(@admin_user_id, 1),
(@admin_user_id, 2),
(@normal_user_id, 1);

-- Stores
INSERT INTO store (id, store_code, store_name, create_dt, creator, update_dt, updater) VALUES
(@store_pc_id, 'PC', 'PcHome', NOW(), 'system', NOW(), 'system'),
(@store_momo_id, 'MOMO', 'MoMo', NOW(), 'system', NOW(), 'system'),
(@store_coup_id, 'COUP', '酷澎', NOW(), 'system', NOW(), 'system'),
(@store_shop_id, 'SHOP', '蝦皮', NOW(), 'system', NOW(), 'system'),
(@store_old_id, 'OLD', '老地方', NOW(), 'system', NOW(), 'system'),
(@store_bone_id, 'BONE', 'BoneBone', NOW(), 'system', NOW(), 'system'),
(@store_li66_id, 'LI66', '光66', NOW(), 'system', NOW(), 'system'),
(@store_luck_id, 'LUCK', '好狗運', NOW(), 'system', NOW(), 'system'),
(@store_park_id, 'PARK', '寵物公園', NOW(), 'system', NOW(), 'system'),
(@store_off_id, 'OFFICEAL', '官網', NOW(), 'system', NOW(), 'system');

-- Tags
INSERT INTO tag (id, tag_code, tag_name, create_dt, creator, update_dt, updater) VALUES
(@tag_gf_id, 'GRAIN_FREE', '無穀', NOW(), 'system', NOW(), 'system'),
(@tag_kit_id, 'KITTEN', '幼貓專用', NOW(), 'system', NOW(), 'system'),
(@tag_hair_id, 'HAIRBALL_CONTROL', '化毛', NOW(), 'system', NOW(), 'system');

-- Ingredients
INSERT INTO ingredient (id, ingredient_code, ingredient_name, create_dt, creator, update_dt, updater) VALUES
(@ing_chicken_id, 'CHICKEN', '雞肉', NOW(), 'system', NOW(), 'system'),
(@ing_deer_id, 'DEER', '鹿肉', NOW(), 'system', NOW(), 'system'),
(@ing_turkey_id, 'TURKEY', '火雞肉', NOW(), 'system', NOW(), 'system'),
(@ing_salmon_id, 'SALMON', '鮭魚', NOW(), 'system', NOW(), 'system'),
(@ing_beef_id, 'BEEF', '牛肉', NOW(), 'system', NOW(), 'system'),
(@ing_tuna_id, 'TUNA', '鮪魚', NOW(), 'system', NOW(), 'system');

-- Company
INSERT INTO company (id, company_code, company_name, create_dt, creator, update_dt, updater) VALUES
(@comp_shep_id, 'SHEP', '牧羊人集團', NOW(), 'system', NOW(), 'system');

-- Brands
INSERT INTO brand (id, brand_code, brand_name, company_id, create_dt, creator, update_dt, updater) VALUES
(@brand_dogcat_id, 'DOGCAT', '汪喵星球', @comp_shep_id, NOW(), 'system', NOW(), 'system'),
(@brand_monster_id, 'MONSTER', '怪獸部落', @comp_shep_id, NOW(), 'system', NOW(), 'system'),
(@brand_heroma_id, 'HEROMA', 'HeroMama', @comp_shep_id, NOW(), 'system', NOW(), 'system'),
(@brand_lady_id, 'LADY', '超凝小姐', @comp_shep_id, NOW(), 'system', NOW(), 'system');

-- Product (Kibble is a type of Product)
INSERT INTO product (id, product_code, product_name, brand_id, product_type, create_dt, creator, update_dt, updater) VALUES
(@prod_dogcat001_id, 'DOGCAT-001', '鮮肉罐', @brand_dogcat_id, 'KIBBLE', NOW(), 'system', NOW(), 'system');

-- Product Tags
INSERT INTO product_tag (product_id, tag_id) VALUES
(@prod_dogcat001_id, @tag_gf_id);

-- Product Variants
-- Variant 1: Turkey Can
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_turkey_can_id, @prod_dogcat001_id, 'DOGCAT-001-TURKEY-85G1C', '田園火雞', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Variant 2: Turkey Box
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_turkey_box_id, @prod_dogcat001_id, 'DOGCAT-001-TURKEY-85G24C', '田園火雞', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system');

-- Variant 3: Beef Can
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_beef_can_id, @prod_dogcat001_id, 'DOGCAT-001-BEEF-85G1C', '草飼牛肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Variant Ingredient Mappings
-- Turkey Can Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_turkey_can_id, @ing_turkey_id, 1),
(UUID(), @var_turkey_can_id, @ing_chicken_id, 2);

-- Turkey Box Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_turkey_box_id, @ing_turkey_id, 1),
(UUID(), @var_turkey_box_id, @ing_chicken_id, 2);

-- Beef Can Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_beef_can_id, @ing_beef_id, 1),
(UUID(), @var_beef_can_id, @ing_chicken_id, 2);

-- Product Details
-- Turkey Can Detail
INSERT INTO product_detail (id, product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_turkey_can_id, '火雞肉、雞心肝、雞蛋黃...', 15.1, 6.0, 75.9, NOW(), 'system', NOW(), 'system');

-- Beef Can Detail
INSERT INTO product_detail (id, product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_beef_can_id, '牛肉、雞肉、雞心肝、雞蛋黃...', 15.8, 6.6, 74.6, NOW(), 'system', NOW(), 'system');

-- Product Price History
-- Turkey Can Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_turkey_can_id, @store_off_id, 46, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_can_id, @store_momo_id, 45, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_can_id, @store_old_id, 43, NOW(), 'system', NOW(), 'system');

-- Turkey Box Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_turkey_box_id, @store_off_id, 1100, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_box_id, @store_momo_id, 900, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_box_id, @store_old_id, 1000, NOW(), 'system', NOW(), 'system');

-- Beef Can Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_beef_can_id, @store_off_id, 46, NOW(), 'system', NOW(), 'system');
