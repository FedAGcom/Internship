package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySearcher {
    Page<CompanyEntity> search(String keyword, String searchCriteria, Pageable pageable);
}
