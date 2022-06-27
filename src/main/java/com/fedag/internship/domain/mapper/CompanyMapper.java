package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.CompanyRequest;
import com.fedag.internship.domain.dto.request.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminCompanyResponse;
import com.fedag.internship.domain.dto.response.user.CompanyResponse;
import com.fedag.internship.domain.entity.CompanyEntity;

public interface CompanyMapper {
    CompanyResponse toResponse(CompanyEntity companyEntity);

    AdminCompanyResponse toAdminResponse(CompanyEntity companyEntity);

    CompanyEntity fromRequest(CompanyRequest companyRequest);

    CompanyEntity fromRequestUpdate(CompanyRequestUpdate companyRequestUpdate);

    CompanyEntity merge(CompanyEntity source, CompanyEntity target);
}
