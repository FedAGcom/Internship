package com.fedag.internship.service;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;

public interface ConfirmationTokenService {

    ConfirmationTokenEntity createTokenForUser(UserEntity userEntity);

    ConfirmationTokenEntity getByToken(String token);

    void deleteToken(ConfirmationTokenEntity token);
}

