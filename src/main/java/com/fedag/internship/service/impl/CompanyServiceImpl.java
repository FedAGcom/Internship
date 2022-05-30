package com.fedag.internship.service.impl;

import com.fedag.internship.domain.CompanyMapper;
import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.exception.CompanyNotFoundException;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto getCompanyById(Long id) {
        CompanyEntity companyEntity = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
        return companyMapper.toDto(companyEntity);
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<CompanyDto> companies = new ArrayList<>();
        companyRepository.findAll()
                .forEach(entity -> companies.add(companyMapper.toDto(entity)));
        return companies;
    }

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        CompanyEntity companyEntity = companyMapper.toEntity(companyDto);
        companyRepository.save(companyEntity);
        return companyDto;
    }

    @Override
    public CompanyDto updateCompany(CompanyDto companyDto) {
        CompanyEntity companyEntity = companyMapper.toEntity(companyDto);
        companyRepository.save(companyEntity);
        return companyDto;
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
