package com.example.catfoodv1.repo.product;

import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Company;
import com.example.catfoodv1.model.entity.product.Product;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.CompanyRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 這是針對 Product Repository 的持久化層整合測試。
 * @DataJpaTest 會設定一個嵌入式記憶體資料庫 (如 H2)，並只載入 JPA 相關的元件。
 * 它會自動設定交易並在每個測試後回滾，確保測試之間的獨立性。
 */
@DataJpaTest
class ProductRepositoryIntegrationTest {

    // TestEntityManager 是一個方便在測試中操作 Entity 的工具，特別適合用來準備資料和驗證狀態。
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    // 由於 Product 與 Brand/Company 有 @NotNull 關聯，我們也需要注入它們的 Repository 來建立前置資料。
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Brand testBrand;

    @BeforeEach
    void setUp() {
        Company company = new Company();
        company.setCompanyCode("TEST_COMP_REPO");
        company.setCompanyName("測試公司Repo");
        Company savedCompany = companyRepository.save(company);

        Brand brand = new Brand();
        brand.setBrandCode("TEST_BRAND_REPO");
        brand.setBrandName("測試品牌Repo");
        brand.setCompany(savedCompany);
        testBrand = brandRepository.save(brand);
    }

}