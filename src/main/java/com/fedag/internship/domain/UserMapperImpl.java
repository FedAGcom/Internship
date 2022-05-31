package com.fedag.internship.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.UserDto;
import com.fedag.internship.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ObjectMapper objectMapper;

    public UserDto toDto(UserEntity userEntity) {
        return objectMapper.convertValue(userEntity, UserDto.class);
    }

    public UserEntity toEntity(UserDto userDto) {

        return objectMapper.convertValue(userDto, UserEntity.class);
    }
}
