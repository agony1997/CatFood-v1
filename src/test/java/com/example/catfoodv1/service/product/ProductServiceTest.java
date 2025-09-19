package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.entity.product.Kibble;
import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.model.entity.product.WetFood;
import com.example.catfoodv1.repo.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceTest.class);

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private final List<Product> productList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        log.info("正在設定測試資料");
        for (int i = 0; i < 5; i++) {
            Kibble kibble = new Kibble();
            kibble.setId(UUID.randomUUID());
            kibble.setProductName("測試乾糧 " + i);
            productList.add(kibble);

            WetFood wetFood = new WetFood();
            wetFood.setId(UUID.randomUUID());
            wetFood.setProductName("測試濕糧 " + i);
            productList.add(wetFood);
        }
        log.info("測試資料設定完成，共建立了 {} 個產品。", productList.size());
    }

}
