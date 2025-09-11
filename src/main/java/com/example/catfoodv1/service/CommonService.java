package com.example.catfoodv1.service;

import com.example.catfoodv1.model.SelectType;
import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.CompanyRepository;
import com.example.catfoodv1.repo.business.StoreRepository;
import com.example.catfoodv1.repo.product.IngredientRepository;
import com.example.catfoodv1.repo.product.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommonService {
    private final CompanyRepository companyRepository;
    private final BrandRepository brandRepository;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;
    private final StoreRepository storeRepository;

    public Set<CommonDto> getAll(@NonNull SelectType type) {
        return switch (type) {
            case BRAND -> getAllBrands();
            case COMPANY -> getAllCompanies();
            case TAG -> getAllTags();
            case STORE -> getAllStores();
            case INGREDIENT -> getAllIngredients();
        };
    }

    private Set<CommonDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(b -> new CommonDto(b.getId(), b.getBrandCode(), b.getBrandName()))
                .collect(Collectors.toSet());
    }

    private Set<CommonDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(c -> new CommonDto(c.getId(), c.getCompanyCode(), c.getCompanyName()))
                .collect(Collectors.toSet());
    }

    private Set<CommonDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(s -> new CommonDto(s.getId(), s.getStoreCode(), s.getStoreName()))
                .collect(Collectors.toSet());
    }

    private Set<CommonDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> new CommonDto(t.getId(), t.getTagCode(), t.getTagName()))
                .collect(Collectors.toSet());
    }

    private Set<CommonDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(i -> new CommonDto(i.getId(), i.getIngredientCode(), i.getIngredientName()))
                .collect(Collectors.toSet());
    }
}
