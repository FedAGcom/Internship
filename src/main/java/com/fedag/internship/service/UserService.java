package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity getUserById(Long id);

    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(Long id, UserEntity user);

    void deleteUser(Long id);
}
