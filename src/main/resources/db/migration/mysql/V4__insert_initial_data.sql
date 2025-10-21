-- V4: Insert initial data (MySQL specific)

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
SET @prod_monster001_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a002';
SET @prod_monster002_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a003';
SET @prod_monster003_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a004';
SET @prod_monster004_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a005';
SET @prod_hero001_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a006';
SET @prod_hero002_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a007';
SET @prod_lady001_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a009';
SET @prod_lady002_id = '90a0a0a0-90a0-90a0-90a0-a0a0a0a0a010';

-- Product Variants
SET @var_turkey_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a001';
SET @var_turkey_box_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a002';
SET @var_beef_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a003';
SET @var_monster_salmon_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a004';
SET @var_monster_salmon_box_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a005';
SET @var_monster_tuna_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a006';
SET @var_monster_chicken_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a007';
SET @var_monster_deer_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a008';
SET @var_monster_beef_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a009';
SET @var_monster_lamb_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a010';
SET @var_monster_duck_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a011';
SET @var_hero_turkey_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a012';
SET @var_hero_chicken_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a013';
SET @var_hero_salmon_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a014';
SET @var_hero_tuna_can_id = 'd0a0a0a0-d0a0-d0a0-d0a0-a0a0a0a0a015';

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

-- Products
INSERT INTO product (id, product_code, product_name, brand_id, product_type, create_dt, creator, update_dt, updater) VALUES
(@prod_dogcat001_id, 'DOGCAT-001', '鮮肉罐', @brand_dogcat_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_monster001_id, 'MONSTER-001', '怪獸海鮮系列', @brand_monster_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_monster002_id, 'MONSTER-002', '怪獸陸地系列', @brand_monster_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_monster003_id, 'MONSTER-003', '怪獸野味系列', @brand_monster_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_monster004_id, 'MONSTER-004', '怪獸特選系列', @brand_monster_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_hero001_id, 'HEROMA-001', 'HeroMama 鮮肉系列', @brand_heroma_id, 'KIBBLE', NOW(), 'system', NOW(), 'system'),
(@prod_hero002_id, 'HEROMA-002', 'HeroMama 海鮮系列', @brand_heroma_id, 'KIBBLE', NOW(), 'system', NOW(), 'system');

-- Product Tags
INSERT INTO product_tag (product_id, tag_id) VALUES
(@prod_dogcat001_id, @tag_gf_id),
(@prod_monster001_id, @tag_gf_id),
(@prod_monster001_id, @tag_kit_id),
(@prod_monster002_id, @tag_gf_id),
(@prod_monster002_id, @tag_hair_id),
(@prod_monster003_id, @tag_gf_id),
(@prod_monster004_id, @tag_gf_id),
(@prod_hero001_id, @tag_gf_id),
(@prod_hero002_id, @tag_gf_id),
(@prod_hero002_id, @tag_kit_id);

-- Product Variants
-- DogCat Products
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_turkey_can_id, @prod_dogcat001_id, 'DOGCAT-001-TURKEY-85G1C', '田園火雞', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_turkey_box_id, @prod_dogcat001_id, 'DOGCAT-001-TURKEY-85G24C', '田園火雞', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_beef_can_id, @prod_dogcat001_id, 'DOGCAT-001-BEEF-85G1C', '草飼牛肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Sea Series (怪獸海鮮系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_monster_salmon_can_id, @prod_monster001_id, 'MONSTER-001-SALMON-85G1C', '怪獸鮭魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_monster_salmon_box_id, @prod_monster001_id, 'MONSTER-001-SALMON-85G24C', '怪獸鮭魚', 85, 24, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_monster_tuna_can_id, @prod_monster001_id, 'MONSTER-001-TUNA-85G1C', '怪獸鮪魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Land Series (怪獸陸地系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_monster_chicken_can_id, @prod_monster002_id, 'MONSTER-002-CHICKEN-85G1C', '怪獸雞肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_monster_beef_can_id, @prod_monster002_id, 'MONSTER-002-BEEF-85G1C', '怪獸牛肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Wild Series (怪獸野味系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_monster_deer_can_id, @prod_monster003_id, 'MONSTER-003-DEER-85G1C', '怪獸鹿肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_monster_lamb_can_id, @prod_monster003_id, 'MONSTER-003-LAMB-85G1C', '怪獸羊肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Monster Premium Series (怪獸特選系列)
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_monster_duck_can_id, @prod_monster004_id, 'MONSTER-004-DUCK-85G1C', '怪獸鴨肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- HeroMama Product Variants
-- HeroMama 鮮肉系列
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_hero_turkey_can_id, @prod_hero001_id, 'HEROMA-001-TURKEY-85G1C', 'Hero 火雞', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_hero_chicken_can_id, @prod_hero001_id, 'HEROMA-001-CHICKEN-85G1C', 'Hero 雞肉', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- HeroMama 海鮮系列
INSERT INTO product_variant (id, product_id, sku, variant_name, package_weight_grams, pack_size, unit_of_measure, create_dt, creator, update_dt, updater) VALUES
(@var_hero_salmon_can_id, @prod_hero002_id, 'HEROMA-002-SALMON-85G1C', 'Hero 鮭魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system'),
(@var_hero_tuna_can_id, @prod_hero002_id, 'HEROMA-002-TUNA-85G1C', 'Hero 鮪魚', 85, 1, 'CAN', NOW(), 'system', NOW(), 'system');

-- Variant Ingredient Mappings
-- DogCat Products
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_turkey_can_id, @ing_turkey_id, 1),
(UUID(), @var_turkey_can_id, @ing_chicken_id, 2),
(UUID(), @var_turkey_box_id, @ing_turkey_id, 1),
(UUID(), @var_turkey_box_id, @ing_chicken_id, 2),
(UUID(), @var_beef_can_id, @ing_beef_id, 1),
(UUID(), @var_beef_can_id, @ing_chicken_id, 2);

-- Monster Product Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_monster_salmon_can_id, @ing_salmon_id, 1),
(UUID(), @var_monster_salmon_can_id, @ing_chicken_id, 2),
(UUID(), @var_monster_salmon_box_id, @ing_salmon_id, 1),
(UUID(), @var_monster_salmon_box_id, @ing_chicken_id, 2),
(UUID(), @var_monster_tuna_can_id, @ing_tuna_id, 1),
(UUID(), @var_monster_tuna_can_id, @ing_chicken_id, 2),
(UUID(), @var_monster_chicken_can_id, @ing_chicken_id, 1),
(UUID(), @var_monster_beef_can_id, @ing_beef_id, 1),
(UUID(), @var_monster_deer_can_id, @ing_deer_id, 1),
(UUID(), @var_monster_deer_can_id, @ing_chicken_id, 2),
(UUID(), @var_monster_lamb_can_id, @ing_beef_id, 1),
(UUID(), @var_monster_duck_can_id, @ing_chicken_id, 1);

-- HeroMama Product Ingredients
INSERT INTO variant_ingredient_mapping (id, product_variant_id, ingredient_id, ingredient_order) VALUES
(UUID(), @var_hero_turkey_can_id, @ing_turkey_id, 1),
(UUID(), @var_hero_turkey_can_id, @ing_chicken_id, 2),
(UUID(), @var_hero_chicken_can_id, @ing_chicken_id, 1),
(UUID(), @var_hero_salmon_can_id, @ing_salmon_id, 1),
(UUID(), @var_hero_salmon_can_id, @ing_chicken_id, 2),
(UUID(), @var_hero_tuna_can_id, @ing_tuna_id, 1),
(UUID(), @var_hero_tuna_can_id, @ing_chicken_id, 2);

-- Product Details
-- DogCat Products
INSERT INTO product_detail (id, product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_turkey_can_id, '火雞肉、雞心肝、雞蛋黃...', 15.1, 6.0, 75.9, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_beef_can_id, '牛肉、雞肉、雞心肝、雞蛋黃...', 15.8, 6.6, 74.6, NOW(), 'system', NOW(), 'system');

-- Monster Product Details
INSERT INTO product_detail (id, product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_monster_salmon_can_id, '鮭魚、雞心肝、雞蛋黃...', 15.0, 6.0, 75.0, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_box_id, '鮭魚、雞心肝、雞蛋黃...', 15.0, 6.0, 75.0, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_tuna_can_id, '鮪魚、雞心肝、雞蛋黃...', 15.2, 6.2, 74.8, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_chicken_can_id, '雞肉、雞心肝、雞蛋黃...', 15.5, 6.5, 74.5, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_beef_can_id, '牛肉、雞心肝、雞蛋黃...', 16.0, 7.0, 74.0, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_deer_can_id, '鹿肉、雞心肝、雞蛋黃...', 16.5, 6.8, 73.5, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_lamb_can_id, '羊肉、雞心肝、雞蛋黃...', 16.2, 7.2, 73.8, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_duck_can_id, '鴨肉、雞心肝、雞蛋黃...', 15.8, 8.0, 74.2, NOW(), 'system', NOW(), 'system');

-- HeroMama Product Details
INSERT INTO product_detail (id, product_variant_id, ingredients, protein_percentage, fat_percentage, moisture_percentage, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_hero_turkey_can_id, 'Hero火雞肉、雞心肝、雞蛋黃...', 15.3, 6.2, 75.5, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_chicken_can_id, 'Hero雞肉、雞心肝、雞蛋黃...', 15.6, 6.4, 74.8, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_salmon_can_id, 'Hero鮭魚、雞心肝、雞蛋黃...', 15.2, 6.1, 75.2, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_tuna_can_id, 'Hero鮪魚、雞心肝、雞蛋黃...', 15.4, 6.3, 74.9, NOW(), 'system', NOW(), 'system');

-- Product Price History
-- DogCat Product Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_turkey_can_id, @store_off_id, 46, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_can_id, @store_momo_id, 45, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_can_id, @store_old_id, 43, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_box_id, @store_off_id, 1100, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_box_id, @store_momo_id, 900, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_turkey_box_id, @store_old_id, 1000, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_beef_can_id, @store_off_id, 46, NOW(), 'system', NOW(), 'system');

-- Monster Sea Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_monster_salmon_can_id, @store_off_id, 50, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_can_id, @store_momo_id, 48, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_can_id, @store_old_id, 45, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_box_id, @store_off_id, 1200, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_box_id, @store_momo_id, 1000, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_salmon_box_id, @store_old_id, 1100, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_tuna_can_id, @store_off_id, 55, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_tuna_can_id, @store_momo_id, 53, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_tuna_can_id, @store_old_id, 50, NOW(), 'system', NOW(), 'system');

-- Monster Land Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_monster_chicken_can_id, @store_off_id, 48, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_chicken_can_id, @store_momo_id, 46, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_chicken_can_id, @store_old_id, 43, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_beef_can_id, @store_off_id, 52, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_beef_can_id, @store_momo_id, 50, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_beef_can_id, @store_old_id, 48, NOW(), 'system', NOW(), 'system');

-- Monster Wild Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_monster_deer_can_id, @store_off_id, 58, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_deer_can_id, @store_momo_id, 55, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_deer_can_id, @store_old_id, 52, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_lamb_can_id, @store_off_id, 60, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_lamb_can_id, @store_momo_id, 57, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_lamb_can_id, @store_old_id, 54, NOW(), 'system', NOW(), 'system');

-- Monster Premium Series Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_monster_duck_can_id, @store_off_id, 62, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_duck_can_id, @store_momo_id, 59, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_monster_duck_can_id, @store_old_id, 56, NOW(), 'system', NOW(), 'system');

-- HeroMama Product Prices
-- HeroMama 鮮肉系列 Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_hero_turkey_can_id, @store_off_id, 52, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_turkey_can_id, @store_momo_id, 50, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_turkey_can_id, @store_old_id, 47, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_chicken_can_id, @store_off_id, 49, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_chicken_can_id, @store_momo_id, 47, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_chicken_can_id, @store_old_id, 44, NOW(), 'system', NOW(), 'system');

-- HeroMama 海鮮系列 Prices
INSERT INTO product_price_history (id, product_variant_id, store_id, price, create_dt, creator, update_dt, updater) VALUES
(UUID(), @var_hero_salmon_can_id, @store_off_id, 56, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_salmon_can_id, @store_momo_id, 54, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_salmon_can_id, @store_old_id, 51, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_tuna_can_id, @store_off_id, 58, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_tuna_can_id, @store_momo_id, 56, NOW(), 'system', NOW(), 'system'),
(UUID(), @var_hero_tuna_can_id, @store_old_id, 53, NOW(), 'system', NOW(), 'system');

-- Add sample product reviews for demonstration
INSERT INTO product_review (id, product_id, account_id, title, rating, comment, create_dt, creator, update_dt, updater) VALUES
(UUID(), @prod_monster001_id, @admin_user_id, '鮭魚罐頭評測', 5, '我家貓咪超愛這個鮭魚罐頭！適口性很好，而且成分單純，沒有不必要的添加物。', NOW(), 'admin', NOW(), 'admin'),
(UUID(), @prod_monster001_id, @normal_user_id, '海鮮系列不錯', 4, '怪獸部落的海鮮系列品質穩定，貓咪吃得很開心，價格也合理。', NOW(), 'normal', NOW(), 'normal'),
(UUID(), @prod_monster002_id, @admin_user_id, '陸地系列評測', 4, '雞肉和牛肉口味都不錯，不過我家貓比較偏愛海鮮系列。', NOW(), 'admin', NOW(), 'admin'),
(UUID(), @prod_monster003_id, @normal_user_id, '野味系列高端', 5, '鹿肉和羊肉都是高蛋白，適合活動量大的貓咪。品質很棒！', NOW(), 'normal', NOW(), 'normal'),
(UUID(), @prod_dogcat001_id, @admin_user_id, '汪喵星球好評', 4, '火雞肉罐頭很香，貓咪一聞到就跑過來了。營養成分也很均衡。', NOW(), 'admin', NOW(), 'admin'),
(UUID(), @prod_hero001_id, @admin_user_id, 'HeroMama 品質優良', 5, 'HeroMama 的鮮肉系列真的很棒！貓咪第一次吃就愛上了，而且營養豐富。', NOW(), 'admin', NOW(), 'admin'),
(UUID(), @prod_hero002_id, @normal_user_id, 'Hero海鮮讚', 4, 'HeroMama 海鮮系列的鮭魚罐很新鮮，貓咪吃得津津有味，推薦！', NOW(), 'normal', NOW(), 'normal');
