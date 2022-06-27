package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.CompanyRequest;
import com.fedag.internship.domain.dto.request.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminCompanyResponse;
import com.fedag.internship.domain.dto.response.user.CompanyResponse;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyMapperImpl implements CompanyMapper {
    private final ObjectMapper objectMapper;

    @Override
    public CompanyResponse toResponse(CompanyEntity companyEntity) {
        return new CompanyResponse()
                .setId(companyEntity.getId())
                .setName(companyEntity.getName())
                .setDescription(companyEntity.getDescription())
                .setRating(companyEntity.getRating())
                .setLocation(companyEntity.getLocation())
                .setLink(companyEntity.getLink())
                .setUserId(companyEntity.getUser().getId())
                .setFavouriteCompanyForUserIds(companyEntity.getUsers()
                        .stream()
                        .map(UserEntity::getId)
                        .collect(Collectors.toList()))
                .setCommentIds(companyEntity.getComments()
                        .stream()
                        .map(CommentEntity::getId)
                        .collect(Collectors.toList()));
    }

    @Override
    public AdminCompanyResponse toAdminResponse(CompanyEntity companyEntity) {
        return new AdminCompanyResponse()
                .setId(companyEntity.getId())
                .setName(companyEntity.getName())
                .setDescription(companyEntity.getDescription())
                .setRating(companyEntity.getRating())
                .setLocation(companyEntity.getLocation())
                .setLink(companyEntity.getLink())
                .setActive(companyEntity.getActive())
                .setUserId(companyEntity.getUser().getId())
                .setFavouriteCompanyForUserIds(companyEntity.getUsers()
                        .stream()
                        .map(UserEntity::getId)
                        .collect(Collectors.toList()))
                .setCommentIds(companyEntity.getComments()
                        .stream()
                        .map(CommentEntity::getId)
                        .collect(Collectors.toList()));
    }

    public CompanyEntity fromRequest(CompanyRequest companyRequest) {
        return objectMapper.convertValue(companyRequest, CompanyEntity.class);
    }

    public CompanyEntity fromRequestUpdate(CompanyRequestUpdate companyRequestUpdate) {
        return objectMapper.convertValue(companyRequestUpdate, CompanyEntity.class);
    }

    @Override
    public CompanyEntity merge(CompanyEntity source, CompanyEntity target) {
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getRating() != null) {
            target.setRating(source.getRating());
        }
        if (source.getLocation() != null) {
            target.setLocation(source.getLocation());
        }
        if (source.getLink() != null) {
            target.setLink(source.getLink());
        }
        return target;
    }
}