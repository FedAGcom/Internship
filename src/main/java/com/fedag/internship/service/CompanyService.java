package com.fedag.internship.service;

import com.fedag.internship.domain.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    CompanyDto getCompanyById(Long id);

    Page<CompanyDto> getAllCompanies(Pageable pageable);

    CompanyDto createCompany(CompanyDto companyDto);

    CompanyDto updateCompany(CompanyDto companyDto);

    void deleteCompany(Long id);

    Page<CompanyDto> searchCompanyByName(String keyword, Pageable pageable);

}
