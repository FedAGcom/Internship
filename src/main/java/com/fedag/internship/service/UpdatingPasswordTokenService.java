package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;

public interface UpdatingPasswordTokenService {

    UpdatingPasswordToken createTokenForUser(UserEntity userEntity, String password);

    UpdatingPasswordToken getByToken(String token);

    void deleteToken(UpdatingPasswordToken token);
}
