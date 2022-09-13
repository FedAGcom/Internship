package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyEntity findById(Long id);

    Page<CompanyEntity> findAll(Pageable pageable);

    Page<CompanyEntity> findAllByActiveTrue(Pageable pageable);

    Page<CompanyEntity> findAllByActiveFalse(Pageable pageable);

    CompanyEntity create(CompanyEntity companyDto);

    CompanyEntity update(Long id, CompanyEntity companyDto);

    void deleteById(Long id);

    void deleteComments(Long id);

    Page<CompanyEntity> searchByName(String keyword, Pageable pageable);
}
