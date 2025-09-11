package com.example.catfoodv1.service.business;

import com.example.catfoodv1.repo.business.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;
}
