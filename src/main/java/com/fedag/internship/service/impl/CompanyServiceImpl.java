package com.fedag.internship.service.impl;

import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto getCompanyById(Long id) {
        CompanyEntity companyEntity = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company", "id", id));
        return companyMapper.toDto(companyEntity);
    }

    @Override
    public Page<CompanyDto> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDto);
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

    @Override
    public Page<CompanyDto> searchCompanyByName(String keyword, Pageable pageable) {
        return companyRepository.search(keyword, "name", pageable)
                .map(companyMapper::toDto);
    }
}
