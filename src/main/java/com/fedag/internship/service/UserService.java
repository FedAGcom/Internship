package com.fedag.internship.service;

import com.fedag.internship.domain.dto.UserDto;
import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity getUserById(long id);

    UserEntity addUser(UserEntity user);

    void deleteUser(long id);

    UserEntity editUser(UserEntity user);
}
