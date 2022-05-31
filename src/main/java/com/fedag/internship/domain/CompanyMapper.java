package com.fedag.internship.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.dto.CompanyRequest;
import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.entity.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyMapper {
    private final ObjectMapper objectMapper;

    public CompanyDto toDto(CompanyEntity companyEntity) {
        return objectMapper.convertValue(companyEntity, CompanyDto.class);
    }

    public CompanyDto toDto(CompanyRequest companyRequest) {
        return objectMapper.convertValue(companyRequest, CompanyDto.class);
    }

    public CompanyEntity toEntity(CompanyDto companyDto) {
        return objectMapper.convertValue(companyDto, CompanyEntity.class);
    }

    public CompanyResponse toResponse(CompanyDto companyDto) {
        return objectMapper.convertValue(companyDto, CompanyResponse.class);
    }
}
