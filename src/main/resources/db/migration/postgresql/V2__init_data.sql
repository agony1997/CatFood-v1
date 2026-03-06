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
