package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyEntity getCompanyById(Long id);

    Page<CompanyEntity> getAllCompanies(Pageable pageable);

    CompanyEntity createCompany(Long userId, CompanyEntity companyDto);

    CompanyEntity updateCompany(Long id, CompanyEntity companyDto);

    void deleteCompany(Long id);

    Page<CompanyDto> searchCompanyByName(String keyword, Pageable pageable);
}
