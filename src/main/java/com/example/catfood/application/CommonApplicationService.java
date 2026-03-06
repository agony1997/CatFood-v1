package com.example.catfood.application;

import com.example.catfood.application.dto.CommonDto;
import com.example.catfood.domain.business.repository.BrandRepository;
import com.example.catfood.domain.business.repository.CompanyRepository;
import com.example.catfood.domain.business.repository.StoreRepository;
import com.example.catfood.domain.common.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonApplicationService {

    private final BrandRepository brandRepository;
    private final CompanyRepository companyRepository;
    private final StoreRepository storeRepository;
    private final TagRepository tagRepository;

    public Set<CommonDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(b -> new CommonDto(b.getId(), b.getBrandCode(), b.getBrandName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(c -> new CommonDto(c.getId(), c.getCompanyCode(), c.getCompanyName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(s -> new CommonDto(s.getId(), s.getStoreCode(), s.getStoreName()))
                .collect(Collectors.toSet());
    }

    public Set<CommonDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> new CommonDto(t.getId(), t.getTagCode(), t.getTagName()))
                .collect(Collectors.toSet());
    }
}
