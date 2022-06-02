package com.fedag.internship.domain;

import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.entity.CompanyEntity;

public interface CompanyMapper {

    CompanyDto toDto(CompanyEntity companyEntity);

    CompanyDto toDto(CompanyRequest companyRequest);

    CompanyEntity toEntity(CompanyDto companyDto);

    CompanyResponse toResponse(CompanyDto companyDto);
}
