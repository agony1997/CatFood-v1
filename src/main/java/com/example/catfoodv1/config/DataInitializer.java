package com.example.catfoodv1.config;

import com.example.catfoodv1.model.entity.auth.Account;
import com.example.catfoodv1.model.entity.auth.Role;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Company;
import com.example.catfoodv1.model.entity.business.Store;
import com.example.catfoodv1.model.entity.product.*;
import com.example.catfoodv1.model.type.PackageUnit;
import com.example.catfoodv1.repo.auth.AccountRepository;
import com.example.catfoodv1.repo.auth.RoleRepository;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.CompanyRepository;
import com.example.catfoodv1.repo.business.StoreRepository;
import com.example.catfoodv1.repo.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("h2")
public class DataInitializer implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final BrandRepository brandRepository;
    private final KibbleRepository kibbleRepository;
    private final WetFoodRepository wetFoodRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductPriceHistoryRepository productPriceHistoryRepository;
    private final TagRepository tagRepository;
    private final StoreRepository storeRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public void run(String... args) throws Exception {
        // 建立角色
        Role userRole = createRoleIfNotFound("ROLE_USER", "一般使用者");
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "管理員");
        // 建立管理員帳號
        createUserIfNotFound("admin", "admin@example.com", "123", "Agony", Set.of(adminRole, userRole));
        createUserIfNotFound("normal", "normal@example.com", "123", "User", Set.of(userRole));
        // 基本測試資料
        createData();
    }

    private Role createRoleIfNotFound(String code, String name) {
        return roleRepository.findByRoleCode(code).orElseGet(() -> roleRepository.save(new Role(code, name)));
    }

    private void createUserIfNotFound(String accountCode, String email, String password, String username, Set<Role> roles) {
        if (accountRepository.findByAccountCode(accountCode).isEmpty()) {
            Account user = new Account();
            user.setAccountCode(accountCode);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password)); // 加密密碼
            user.setDisplayName(username);
            user.setRoles(roles);
            accountRepository.save(user);
        }
    }

    private void createData() {
        List<Store> stores = createStore();
        Map<String, Store> storeMap = stores.stream()
                .collect(Collectors.toMap(Store::getStoreCode, Function.identity()));

        List<Tag> allTags = createTag();
        List<Ingredient> allIngredients = createIngredients();
        createShepherdTechnologyGroup(allTags, allIngredients, storeMap);
    }

    private List<Store> createStore() {
        return storeRepository.saveAll(List.of(
                Store.builder().storeCode("PC").storeName("PcHome").build(),
                Store.builder().storeCode("MOMO").storeName("MoMo").build(),
                Store.builder().storeCode("COUP").storeName("酷澎").build(),
                Store.builder().storeCode("SHOP").storeName("蝦皮").build(),
                Store.builder().storeCode("OLD").storeName("老地方").build(),
                Store.builder().storeCode("BONE").storeName("BoneBone").build(),
                Store.builder().storeCode("LI66").storeName("光66").build(),
                Store.builder().storeCode("LUCK").storeName("好狗運").build(),
                Store.builder().storeCode("PARK").storeName("寵物公園").build(),
                Store.builder().storeCode("OFFICEAL").storeName("官網").build()
        ));
    }

    private List<Tag> createTag() {
        // Tag 只應該包含描述性標籤
        return tagRepository.saveAll(List.of(
                Tag.builder().tagCode("GRAIN_FREE").tagName("無穀").build(),
                Tag.builder().tagCode("KITTEN").tagName("幼貓專用").build(),
                Tag.builder().tagCode("HAIRBALL_CONTROL").tagName("化毛").build()
        ));
    }

    private List<Ingredient> createIngredients() {
        // 主要成分應該儲存在 Ingredient 表格
        return ingredientRepository.saveAll(List.of(
                new Ingredient(null, "CHICKEN", "雞肉"),
                new Ingredient(null, "DEER", "鹿肉"),
                new Ingredient(null, "TURKEY", "火雞肉"),
                new Ingredient(null, "SALMON", "鮭魚"),
                new Ingredient(null, "BEEF", "牛肉"),
                new Ingredient(null, "TUNA", "鮪魚")
        ));
    }

    /**
     * 公司 : 牧羊人集團
     * 旗下品牌 : 汪喵星球、怪獸部落、HeroMama、超凝小姐
     */
    private void createShepherdTechnologyGroup(List<Tag> allTags, List<Ingredient> allIngredients, Map<String, Store> storeMap) {
        Company company = companyRepository.save(Company.builder().companyCode("SHEP").companyName("牧羊人集團").build());

        List<Brand> brands = brandRepository.saveAll(List.of(
                Brand.builder().brandCode("DOGCAT").brandName("汪喵星球").company(company).build(),
                Brand.builder().brandCode("MONSTER").brandName("怪獸部落").company(company).build(),
                Brand.builder().brandCode("HEROMA").brandName("HeroMama").company(company).build(),
                Brand.builder().brandCode("LADY").brandName("超凝小姐").company(company).build()
        ));

        brands.forEach(brand -> {
            switch (brand.getBrandCode()) {
                case "DOGCAT" -> {
                    Kibble kibble = new Kibble();
                    kibble.setBrand(brand);
                    kibble.setProductCode("DOGCAT-001");
                    kibble.setProductName("鮮肉罐");
                    kibble.setTags(allTags.stream().filter(t -> t.getTagCode().equals("GRAIN_FREE")).toList());

                    ProductVariant turkeyCanVariant = new ProductVariant();
                    turkeyCanVariant.setPackageWeightGrams(85);
                    turkeyCanVariant.setUnitOfMeasure(PackageUnit.CAN);
                    turkeyCanVariant.setPackSize(1);
                    turkeyCanVariant.setProduct(kibble);
                    turkeyCanVariant.setVariantName("田園火雞");

                    // 設定主成分順序：1. 火雞, 2. 雞肉
                    VariantIngredientMapping turkey = new VariantIngredientMapping(null, turkeyCanVariant, findIngredient(allIngredients, "TURKEY"), 1);
                    VariantIngredientMapping chickenForTurkey = new VariantIngredientMapping(null, turkeyCanVariant, findIngredient(allIngredients, "CHICKEN"), 2);
                    turkeyCanVariant.setVariantIngredients(List.of(turkey, chickenForTurkey));

                    ProductDetail detail = new ProductDetail();
                    detail.setIngredients("火雞肉、雞心肝、雞蛋黃...");
                    detail.setProteinPercentage(new BigDecimal("15.1"));
                    detail.setFatPercentage(new BigDecimal(6));
                    detail.setMoisturePercentage(new BigDecimal("75.9"));
                    turkeyCanVariant.setDetail(detail);

                    turkeyCanVariant.setPriceHistory(createPriceHistories(turkeyCanVariant, storeMap, "OFFICEAL:46", "MOMO:45", "OLD:43"));
                    
                    ProductVariant turkeyBoxVariant = new ProductVariant();
                    turkeyBoxVariant.setPackageWeightGrams(85*24);
                    turkeyBoxVariant.setUnitOfMeasure(PackageUnit.CAN); // A box of 24 cans
                    turkeyBoxVariant.setPackSize(24);
                    turkeyBoxVariant.setProduct(kibble);
                    turkeyBoxVariant.setVariantName("田園火雞");

                    // 同樣設定主成分順序
                    VariantIngredientMapping turkeyForBox = new VariantIngredientMapping(null, turkeyBoxVariant, findIngredient(allIngredients, "TURKEY"), 1);
                    VariantIngredientMapping chickenForTurkeyBox = new VariantIngredientMapping(null, turkeyBoxVariant, findIngredient(allIngredients, "CHICKEN"), 2);
                    turkeyBoxVariant.setVariantIngredients(List.of(turkeyForBox, chickenForTurkeyBox));

                    turkeyBoxVariant.setPriceHistory(createPriceHistories(turkeyBoxVariant, storeMap, "OFFICEAL:1100", "MOMO:900", "OLD:1000"));

                    ProductVariant variant2 = new ProductVariant();
                    variant2.setPackageWeightGrams(85);
                    variant2.setUnitOfMeasure(PackageUnit.CAN);
                    variant2.setPackSize(1);
                    variant2.setProduct(kibble);
                    variant2.setVariantName("草飼牛肉");

                    // 設定主成分順序：1. 牛肉, 2. 雞肉
                    VariantIngredientMapping beef = new VariantIngredientMapping(null, variant2, findIngredient(allIngredients, "BEEF"), 1);
                    VariantIngredientMapping chickenForBeef = new VariantIngredientMapping(null, variant2, findIngredient(allIngredients, "CHICKEN"), 2);
                    variant2.setVariantIngredients(List.of(beef, chickenForBeef));

                    ProductDetail detail2 = new ProductDetail();
                    detail2.setIngredients("牛肉、雞肉、雞心肝、雞蛋黃...");
                    detail2.setProteinPercentage(new BigDecimal("15.8"));
                    detail2.setFatPercentage(new BigDecimal("6.6"));
                    detail2.setMoisturePercentage(new BigDecimal("74.6"));
                    variant2.setDetail(detail2);

                    variant2.setPriceHistory(createPriceHistories(variant2, storeMap, "OFFICEAL:46"));

                    kibble.setVariants(List.of(turkeyCanVariant, turkeyBoxVariant, variant2));

                    kibbleRepository.save(kibble);
                }
//                case "MONSTER" -> {
//                }
//                case "HEROMA" -> {
//                }
//                case "LADY" -> {
//                }
            }
        });
    }

    private List<ProductPriceHistory> createPriceHistories(ProductVariant variant, Map<String, Store> storeMap, String... storePrices) {
        return Arrays.stream(storePrices)
                .map(sp -> {
                    String[] parts = sp.split(":");
                    String storeCode = parts[0];
                    Integer price = Integer.parseInt(parts[1]);
                    Store store = storeMap.get(storeCode);

                    return new ProductPriceHistory(null, variant, store, price);
                })
                .collect(Collectors.toList());
    }

    private Ingredient findIngredient(List<Ingredient> ingredients, String code) {
        return ingredients.stream()
                .filter(i -> i.getIngredientCode().equals(code))
                .findFirst().orElseThrow();
    }
}
