package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.RegistrationRequest;
import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.dto.request.UserRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminUserResponse;
import com.fedag.internship.domain.dto.response.user.UserResponse;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ObjectMapper objectMapper;

    @Override
    public UserResponse toResponse(UserEntity userEntity) {
        UserResponse result = new UserResponse();
        if (userEntity.getCompany() != null) {
            result.setCompanyId(userEntity.getCompany().getId());
        }
        return result
                .setId(userEntity.getId())
                .setEmail(userEntity.getEmail())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setCreated(userEntity.getCreated())
                .setCommentIds(userEntity.getComments()
                        .stream()
                        .map(CommentEntity::getId)
                        .collect(Collectors.toList()))
                .setFavouriteCompanyIds(userEntity.getFavouriteCompanies()
                        .stream()
                        .map(CompanyEntity::getId)
                        .collect(Collectors.toList()))
                .setFavouriteTraineePositionIds(userEntity.getFavouriteTraineePositions()
                        .stream()
                        .map(TraineePositionEntity::getId)
                        .collect(Collectors.toList()));
    }

    @Override
    public AdminUserResponse toAdminResponse(UserEntity userEntity) {
        AdminUserResponse result = new AdminUserResponse();
        if (userEntity.getCompany() != null) {
            result.setCompanyId(userEntity.getCompany().getId());
        }
        return result
                .setId(userEntity.getId())
                .setEmail(userEntity.getEmail())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setRole(userEntity.getRole().name())
                .setEnabled(userEntity.getEnabled())
                .setCreated(userEntity.getCreated())
                .setCommentIds(userEntity.getComments()
                        .stream()
                        .map(CommentEntity::getId)
                        .collect(Collectors.toList()))
                .setFavouriteCompanyIds(userEntity.getFavouriteCompanies()
                        .stream()
                        .map(CompanyEntity::getId)
                        .collect(Collectors.toList()))
                .setFavouriteTraineePositionIds(userEntity.getFavouriteTraineePositions()
                        .stream()
                        .map(TraineePositionEntity::getId)
                        .collect(Collectors.toList()));
    }

    @Override
    public UserEntity fromRequest(UserRequest userRequest) {
        return objectMapper.convertValue(userRequest, UserEntity.class);
    }

    @Override
    public UserEntity fromRequestUpdate(UserRequestUpdate userRequestUpdate) {
        return objectMapper.convertValue(userRequestUpdate, UserEntity.class);
    }

    @Override
    public UserEntity fromRegistrationRequest(RegistrationRequest registrationRequest) {
        return objectMapper.convertValue(registrationRequest, UserEntity.class);
    }

    @Override
    public UserEntity merge(UserEntity source, UserEntity target) {
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getFirstName() != null) {
            target.setFirstName(source.getFirstName());
        }
        if (source.getLastName() != null) {
            target.setLastName(source.getLastName());
        }
        return target;
    }
}
