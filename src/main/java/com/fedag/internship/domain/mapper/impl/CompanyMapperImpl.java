package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private final ObjectMapper objectMapper;

    @Override
    public CompanyDto toDto(CompanyEntity companyEntity) {
        return objectMapper.convertValue(companyEntity, CompanyDto.class);
    }

    @Override
    public CompanyDto toDto(CompanyRequest companyRequest) {
        return objectMapper.convertValue(companyRequest, CompanyDto.class);
    }

    @Override
    public CompanyEntity toEntity(CompanyDto companyDto) {
        return objectMapper.convertValue(companyDto, CompanyEntity.class);
    }

    @Override
    public CompanyResponse toResponse(CompanyDto companyDto) {
        return objectMapper.convertValue(companyDto, CompanyResponse.class);
    }
}
