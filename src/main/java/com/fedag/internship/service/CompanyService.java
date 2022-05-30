package com.fedag.internship.service;

import com.fedag.internship.domain.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto getCompanyById(Long id);

    List<CompanyDto> getAllCompanies();

    CompanyDto createCompany(CompanyDto companyDto);

    CompanyDto updateCompany(CompanyDto companyDto);

    void deleteCompany(Long id);

}
