package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.dto.request.UserRequestUpdate;
import com.fedag.internship.domain.dto.response.UserResponse;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ObjectMapper objectMapper;

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
                        .collect(Collectors.toList()));
    }

    public UserEntity fromRequest(UserRequest userRequest) {
        return objectMapper.convertValue(userRequest, UserEntity.class);
    }

    public UserEntity fromRequestUpdate(UserRequestUpdate userRequestUpdate) {
        return objectMapper.convertValue(userRequestUpdate, UserEntity.class);
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
