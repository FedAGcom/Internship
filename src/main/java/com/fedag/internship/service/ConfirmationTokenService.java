package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.entity.ConfirmationTokenEntity;

public interface ConfirmationTokenService {

    ConfirmationTokenEntity createTokenForUser(UserEntity userEntity);

    ConfirmationTokenEntity getByToken(String token);

    void deleteToken(ConfirmationTokenEntity token);
}

