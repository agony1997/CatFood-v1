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
