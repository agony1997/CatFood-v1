package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.dto.product.WetFoodViewDto;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Store;
import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.model.entity.product.ProductPriceHistory;
import com.example.catfoodv1.model.entity.product.ProductVariant;
import com.example.catfoodv1.model.entity.product.WetFood;
import com.example.catfoodv1.repo.product.ProductRepository;
import com.example.catfoodv1.model.type.PackageUnit;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<WetFoodViewDto> getFakeData() {
        List<WetFoodViewDto> fakeDataList = new ArrayList<>();

        // --- 品牌與商店 ---
        Brand brandRoyalCanin = new Brand(); // 皇家
        brandRoyalCanin.setBrandName("Royal Canin (皇家)");

        Brand brandWellness = new Brand(); // Wellness
        brandWellness.setBrandName("Wellness CORE");

        Store storePetSmart = new Store(); // 寵物公園
        storePetSmart.setStoreName("寵物公園");

        Store storeLocalShop = new Store(); // 社區寵物店
        storeLocalShop.setStoreName("社區寵物店");

        // --- 數據 1: 皇家 KIP 幼貓主食肉泥 (單包) ---
        WetFood product1 = new WetFood();
        product1.setId(UUID.randomUUID());
        product1.setProductName("KIP 幼貓主食肉泥");
        product1.setBrand(brandRoyalCanin);

        ProductVariant variant1 = new ProductVariant();
        variant1.setId(UUID.randomUUID());
        variant1.setProduct(product1);
        variant1.setVariantDisplayName("85g 餐包");
        variant1.setPackageWeightGrams(85);
        variant1.setPackSize(1);
        variant1.setUnitOfMeasure(PackageUnit.POUCH);

        // 價格記錄 1-1: 在寵物公園的價格
        ProductPriceHistory priceHistory1 = new ProductPriceHistory(UUID.randomUUID(), variant1, storePetSmart, new BigDecimal("45.00"));
        priceHistory1.setCreateDT(LocalDateTime.now().minusDays(2));
        fakeDataList.add(WetFoodViewDto.from(priceHistory1));

        // 價格記錄 1-2: 同樣的商品，在社區寵物店的價格
        ProductPriceHistory priceHistory2 = new ProductPriceHistory(UUID.randomUUID(), variant1, storeLocalShop, new BigDecimal("42.50"));
        priceHistory2.setCreateDT(LocalDateTime.now().minusDays(5));
        fakeDataList.add(WetFoodViewDto.from(priceHistory2));

        // --- 數據 2: Wellness CORE 經典肉醬主食罐 (擁有多個規格) ---
        WetFood product2 = new WetFood(); // 這是同一個產品系列
        product2.setId(UUID.randomUUID());
        product2.setProductName("經典肉醬主食罐");
        product2.setBrand(brandWellness);

        // 規格 2-1: 雞肉火雞肉雞肝口味 (單罐)
        ProductVariant variant2_1 = new ProductVariant();
        variant2_1.setId(UUID.randomUUID());
        variant2_1.setProduct(product2);
        variant2_1.setVariantDisplayName("雞肉火雞肉雞肝口味");
        variant2_1.setPackageWeightGrams(85);
        variant2_1.setPackSize(1);
        variant2_1.setUnitOfMeasure(PackageUnit.CAN);
        ProductPriceHistory priceHistory3 = new ProductPriceHistory(UUID.randomUUID(), variant2_1, storePetSmart, new BigDecimal("75.00"));
        priceHistory3.setCreateDT(LocalDateTime.now().minusHours(10));
        fakeDataList.add(WetFoodViewDto.from(priceHistory3));

        // 規格 2-2: 同一個產品系列，但不同規格 -> 室內貓配方 (24罐裝)
        ProductVariant variant2_2 = new ProductVariant();
        variant2_2.setId(UUID.randomUUID());
        variant2_2.setProduct(product2); // 關聯到同一個 product2
        variant2_2.setVariantDisplayName("室內貓配方 (24罐裝)");
        variant2_2.setPackageWeightGrams(85);
        variant2_2.setPackSize(24);
        variant2_2.setUnitOfMeasure(PackageUnit.CAN);
        ProductPriceHistory priceHistory4 = new ProductPriceHistory(UUID.randomUUID(), variant2_2, storeLocalShop, new BigDecimal("1750.00"));
        priceHistory4.setCreateDT(LocalDateTime.now().minusDays(1));
        fakeDataList.add(WetFoodViewDto.from(priceHistory4));

        return fakeDataList;
    }

}
