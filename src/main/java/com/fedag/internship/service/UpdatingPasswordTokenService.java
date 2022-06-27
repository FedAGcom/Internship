package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;

public interface UpdatingPasswordTokenService {
    UpdatingPasswordToken create(UserEntity userEntity, String password);

    UpdatingPasswordToken findByToken(String token);

    void delete(UpdatingPasswordToken token);
}
