package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.entity.CompanyEntity;

public interface CompanyMapper {
    CompanyResponse toResponse(CompanyEntity companyEntity);

    CompanyEntity fromRequest(CompanyRequest companyRequest);

    CompanyEntity fromRequestUpdate(CompanyRequestUpdate companyRequestUpdate);

    CompanyEntity merge(CompanyEntity source, CompanyEntity target);
}
