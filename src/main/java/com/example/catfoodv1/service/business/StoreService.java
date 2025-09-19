package com.example.catfoodv1.service.business;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.model.entity.business.Brand;
import com.example.catfoodv1.model.entity.business.Store;
import com.example.catfoodv1.repo.business.BrandRepository;
import com.example.catfoodv1.repo.business.CompanyRepository;
import com.example.catfoodv1.repo.business.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public CommonDto save(CommonDto dto) {
        Store store = storeRepository.save(new Store(null, dto.getCode(), dto.getName()));
        return new CommonDto(store.getId(), store.getStoreCode(), store.getStoreName());
    }
}
