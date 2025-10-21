package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Company;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.CompanyRepository;
import com.example.catfoodv1.repo.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 這是針對 ProductService 的整合測試。
 * @SpringBootTest 會載入完整的 Spring Context，讓我們可以測試 Service 層到資料庫的完整流程。
 * 這裡我們不再使用 Mockito，而是與真實的（或記憶體中的）資料庫進行互動。
 */
@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Brand savedBrand;

    @BeforeEach
    void setUp() {
        // 清理資料庫以確保測試隔離
        productRepository.deleteAll();
        brandRepository.deleteAll();
        companyRepository.deleteAll();

        // 建立必要的關聯資料
        Company company = new Company();
        company.setCompanyCode("TEST_COMP");
        company.setCompanyName("測試公司");
        Company savedCompany = companyRepository.save(company);

        Brand brand = new Brand();
        brand.setBrandCode("TEST_BRAND");
        brand.setBrandName("測試品牌");
        brand.setCompany(savedCompany);
        savedBrand = brandRepository.save(brand);
    }
}