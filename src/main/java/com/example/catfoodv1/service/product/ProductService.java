package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.model.entity.product.ProductPriceHistory;
import com.example.catfoodv1.repo.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private Product getEntityById(UUID id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product entity = getEntityById(product.getId());
        return productRepository.save(entity);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(getEntityById(id).getId());
    }

    /**
     * 獲取所有產品資訊並轉換為用於前端 Grid 顯示的 DTO。
     * 使用 @Transactional(readOnly = true) 來確保在方法執行期間，
     * 所有延遲載入的關聯實體（如 variants, priceHistories）都能被成功讀取。
     *
     * @return WetFoodViewDto 的集合
     */
    @Transactional(readOnly = true)
    public Set<WetFoodViewDto> getAllToDto() {
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
                            // 組合顯示名稱
                            dto.setDisplayName(String.format("%s - %s", product.getProductName(), variant.getVariantName()));
                            dto.setStoreName(lowestPrice.getStore().getStoreName());
                            dto.setPrice(lowestPrice.getPrice());
                            dto.setUpdateDT(lowestPrice.getUpdateDT());
                            dto.calculatePerPrice(variant.getPackageWeightGrams());

                            // 填入明細資訊 (其他價格)
                            Set<WetFoodViewDto.Detail> details = variant.getPriceHistory().stream()
                                    // 過濾掉最低價的那一筆
                                    .filter(ph -> !ph.getId().equals(lowestPrice.getId()))
                                    .map(ph -> new WetFoodViewDto.Detail(
                                            ph.getStore().getStoreName(),
                                            ph.getPrice(),
                                            null, // 明細中的百克價格暫不計算
                                            ph.getUpdateDT()))
                                    .collect(Collectors.toSet());
                            dto.setDetails(details);

                            return dto;
                        }))
                .filter(Objects::nonNull) // 過濾掉沒有價格的項目
                .collect(Collectors.toSet());
    }
}
