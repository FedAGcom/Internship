package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.UserRequest;
import com.fedag.internship.domain.dto.UserRequestUpdate;
import com.fedag.internship.domain.dto.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;

public interface UserMapper {
    UserResponse toResponse(UserEntity userEntity);

    UserEntity fromRequest(UserRequest userRequest);

    UserEntity fromRequestUpdate(UserRequestUpdate userRequestUpdate);

    UserEntity merge(UserEntity source, UserEntity target);
}
