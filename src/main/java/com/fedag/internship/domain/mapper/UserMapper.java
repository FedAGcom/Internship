package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.dto.request.UserRequestUpdate;
import com.fedag.internship.domain.dto.response.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;

public interface UserMapper {
    UserResponse toResponse(UserEntity userEntity);

    UserEntity fromRequest(UserRequest userRequest);

    UserEntity fromRequestUpdate(UserRequestUpdate userRequestUpdate);

    UserEntity merge(UserEntity source, UserEntity target);
}
