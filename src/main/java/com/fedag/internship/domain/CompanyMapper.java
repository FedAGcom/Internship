package com.fedag.internship.domain;

import com.fedag.internship.domain.dto.CompanyDto;
import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

    public CompanyDto toDto(CompanyEntity companyEntity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(companyEntity.getId());
        companyDto.setName(companyEntity.getName());
        companyDto.setDescription(companyEntity.getDescription());
        companyDto.setLocation(companyEntity.getLocation());
        companyDto.setRating(companyEntity.getRating());
        companyDto.setLink(companyEntity.getLink());
        return companyDto;
    }

    public CompanyEntity toEntity(CompanyDto companyDto) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(companyDto.getId());
        companyEntity.setName(companyDto.getName());
        companyEntity.setDescription(companyDto.getDescription());
        companyEntity.setRating(companyDto.getRating());
        companyEntity.setLocation(companyDto.getLocation());
        companyEntity.setLink(companyDto.getLink());
        return companyEntity;
    }
}
