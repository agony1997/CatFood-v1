package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.dto.product.PriceAddDto;
import com.example.catfoodv1.model.dto.product.ProductCreateDto;
import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Store;
import com.example.catfoodv1.model.entity.product.*;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.StoreRepository;
import com.example.catfoodv1.repo.product.IngredientRepository;
import com.example.catfoodv1.repo.product.ProductRepository;
import com.example.catfoodv1.repo.product.ProductVariantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final IngredientRepository ingredientRepository;
    private final StoreRepository storeRepository;
    private final BrandRepository brandRepository;

    @Transactional
    @CacheEvict(value = "wetFoodList", allEntries = true)
    public void createProduct(ProductCreateDto dto) {
        Brand brand = brandRepository.getReferenceById(dto.getBrandId());
        Ingredient ingredient = ingredientRepository.getReferenceById(dto.getIngredientId());
        Store store = storeRepository.getReferenceById(dto.getStoreId());

        Product product = new WetFood();
        ProductDetail detail = new ProductDetail();
        ProductVariant variant = new ProductVariant();
        ProductPriceHistory priceHistory = new ProductPriceHistory();
        VariantIngredientMapping mapping = new VariantIngredientMapping();

        mapping.setVariant(variant);
        mapping.setIngredient(ingredient);
        mapping.setIngredientOrder(1);

        product.setBrand(brand);
        product.setVariants(List.of(variant));
        product.setProductCode(UUID.randomUUID().toString().substring(0, 8));
        product.setProductName(dto.getProductName());

        detail.setVariant(variant);
        detail.setMoisturePercentage(dto.getMoisturePercentage());
        detail.setProteinPercentage(dto.getProteinPercentage());
        detail.setFatPercentage(dto.getFatPercentage());
        detail.setCarbsPercentage(dto.getCarbsPercentage());

        variant.setProduct(product);
        variant.setDetail(detail);
        variant.setVariantIngredients(List.of(mapping));
        variant.setPriceHistory(List.of(priceHistory));
        variant.setPackSize(dto.getPackSize());
        variant.setVariantName(dto.getFlavorName());
        variant.setUnitOfMeasure(dto.getUnitOfMeasure());
        variant.setPackageWeightGrams(dto.getGrams());

        priceHistory.setStore(store);
        priceHistory.setVariant(variant);
        priceHistory.setPrice(dto.getPrice());

        log.info("Save new Product : {}", product);
        log.info("Save new ProductVariant : {}", variant);
        log.info("Save new ProductDetail : {}", detail);
        log.info("Save new VariantIngredientMapping : {}", mapping);
        log.info("Save new PriceHistory : {}", priceHistory);
        log.info("Bind Brand : {}", brand);
        log.info("Bind Ingredient : {}", ingredient);
        log.info("Bind Store : {}", store);

        productRepository.save(product);
    }

    /**
     *  為一個已存在的產品規格新增一筆價格紀錄。
     */
    @Transactional
    @CacheEvict(value = "wetFoodList", allEntries = true)
    public void addPriceToVariant(PriceAddDto dto) {
        ProductVariant variant = productVariantRepository.findById(dto.getVariantId())
                .orElseThrow(() -> new EntityNotFoundException("找不到指定的產品規格，ID: " + dto.getVariantId()));
        Store store = storeRepository.getReferenceById(dto.getStoreId());

        ProductPriceHistory newPrice = new ProductPriceHistory(variant, store, dto.getPrice());
        variant.getPriceHistory().add(newPrice);
    }

    /**
     * 為一個已存在的產品新增一筆規格+價錢。
     */
    @Transactional
    public void addVariant() {

    }

    /**
     * 獲取所有產品資訊並轉換為用於前端 Grid 顯示的 DTO。
     * 使用 @Transactional(readOnly = true) 來確保在方法執行期間，
     * 所有延遲載入的關聯實體（如 variants, priceHistories）都能被成功讀取。
     *
     * @return WetFoodViewDto 的集合
     */
    @Transactional(readOnly = true)
    @Cacheable("wetFoodList")
    public List<WetFoodViewDto> getAllToDto() {
        return productRepository.findAll().stream()
                .flatMap(product -> product.getVariants().stream()
                        .map(variant -> {
                            // 找出此規格最低價的歷史紀錄
                            Optional<ProductPriceHistory> lowestPriceOpt = variant.getPriceHistory().stream()
                                    .min(Comparator.comparing(ProductPriceHistory::getPrice));
                            // 如果沒有價格資訊，則不顯示此項目
                            if (lowestPriceOpt.isEmpty()) {
                                return null;
                            }
                            ProductPriceHistory lowestPrice = lowestPriceOpt.get();

                            // 建立 DTO 並填入主要資訊 (以最低價為主)
                            WetFoodViewDto dto = new WetFoodViewDto();
                            dto.setProductId(product.getId());
                            dto.setVariantId(variant.getId());
                            dto.setBrandName(product.getBrand().getBrandName());
                            dto.setUnit(variant.getUnitOfMeasure());
                            // 組合顯示名稱
                            dto.setDisplayName(String.format("%s - %s", product.getProductName(), variant.getVariantName()));
                            dto.setStoreName(lowestPrice.getStore().getStoreName());
                            dto.setPrice(lowestPrice.getPrice());
                            dto.setUpdateDT(lowestPrice.getUpdateDT());
                            dto.calculatePerPrice(variant.getPackageWeightGrams());

                            // 填入明細資訊 (其他價格)
                            dto.setDetails(variant.getPriceHistory().stream()
                                    .filter(ph -> !ph.getId().equals(lowestPrice.getId()))// 過濾掉最低價的那一筆
                                    .map(ph -> {
                                        WetFoodViewDto detail = new WetFoodViewDto();
                                        detail.setBrandName(dto.getBrandName());
                                        detail.setDisplayName(dto.getDisplayName());
                                        detail.setStoreName(ph.getStore().getStoreName());
                                        detail.setUnit(dto.getUnit());
                                        detail.setPrice(ph.getPrice());
                                        detail.setUpdateDT(ph.getUpdateDT());
                                        detail.calculatePerPrice(variant.getPackageWeightGrams());
                                        return detail;
                                    })
                                    .sorted(Comparator.comparing(WetFoodViewDto::getPrice))
                                    .toList());
                            return dto;
                        }))
                .filter(Objects::nonNull) // 過濾掉沒有價格的項目
                .sorted(Comparator.comparing(WetFoodViewDto::getBrandName)
                        .thenComparing(WetFoodViewDto::getDisplayName)
                        .thenComparing(WetFoodViewDto::getUnit)
                        .thenComparing(WetFoodViewDto::getPrice))
                .collect(Collectors.toList());
    }
}
