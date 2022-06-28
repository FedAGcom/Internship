package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;

public interface UpdatingPasswordService {
    void changeUserPassword(String newPassword, String oldPassword);

    UserEntity confirm(String token);
}
