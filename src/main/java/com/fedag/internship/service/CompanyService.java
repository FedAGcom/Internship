package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyEntity findById(Long id);

    Page<CompanyEntity> findAll(Pageable pageable);

    CompanyEntity create(Long userId, CompanyEntity companyDto);

    CompanyEntity update(Long id, CompanyEntity companyDto);

    void deleteById(Long id);

    Page<CompanyEntity> searchByName(String keyword, Pageable pageable);
}
