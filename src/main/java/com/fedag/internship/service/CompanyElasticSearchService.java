package com.fedag.internship.service;

import com.fedag.internship.domain.document.CompanyElasticSearchEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyElasticSearchService {
    CompanyElasticSearchEntity saveCompany(CompanyEntity companyEntity);

    Page<CompanyElasticSearchEntity> searchByName(Pageable pageable, String name);
}
