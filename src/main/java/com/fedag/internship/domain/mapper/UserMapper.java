package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.UserDto;
import com.fedag.internship.domain.entity.UserEntity;

public interface UserMapper {
    UserDto toDto(UserEntity userEntity);

    UserEntity toEntity(UserDto userDto);
}
