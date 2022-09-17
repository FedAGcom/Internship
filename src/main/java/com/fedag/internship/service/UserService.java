package com.fedag.internship.service;


import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity findById(Long id);

    Page<UserEntity> findAllByRoleUser(Pageable pageable);

    Page<UserEntity> findAllByRoleBlocked(Pageable pageable);

    Page<UserEntity> findAllByRoleDeleted(Pageable pageable);

    Page<UserEntity> findAll(Pageable pageable);

    UserEntity create(UserEntity user);

    UserEntity update(Long id, UserEntity user);

    void blockById(Long id);

    void deleteById();

    void recoveryById();

    void deleteComments(Long id);
}
