package com.fedag.internship.service;

import com.fedag.internship.domain.dto.request.CompanyRequest;
import com.fedag.internship.domain.entity.CompanyELSEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ELSCompanyService {
    CompanyELSEntity saveCompany(CompanyEntity companyEntity);

    Page<CompanyELSEntity> searchByName(Pageable pageable, String name);
}
