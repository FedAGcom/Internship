package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;

public interface UpdatingPasswordService {
    UserEntity changeUserPassword(UserEntity userEntity, String password);

    boolean checkIfValidOldPassword(UserEntity userEntity, String oldPassword);
}
