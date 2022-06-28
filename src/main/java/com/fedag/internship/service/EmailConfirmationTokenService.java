package com.fedag.internship.service;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;

public interface EmailConfirmationTokenService {
    ConfirmationTokenEntity create(UserEntity userEntity);

    ConfirmationTokenEntity findByToken(String token);

    void delete(ConfirmationTokenEntity token);
}

