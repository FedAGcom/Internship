package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;

public interface UpdatingPasswordService {
    UserEntity changeUserPassword(String newPassword, String oldPassword);
}
