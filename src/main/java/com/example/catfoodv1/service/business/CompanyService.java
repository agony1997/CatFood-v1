package com.example.catfoodv1.service.business;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.model.entity.business.Company;
import com.example.catfoodv1.repo.business.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CommonDto save(CommonDto dto) {
        Company company = companyRepository.save(new Company(null, dto.getCode(), dto.getName()));
        return new CommonDto(company.getId(), company.getCompanyCode(), company.getCompanyName());
    }

}
