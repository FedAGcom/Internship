package com.fedag.internship.register;

import com.fedag.internship.domain.entity.UserEntity;

public interface ConfirmationTokenService {

    ConfirmationTokenEntity createTokenForUser(UserEntity userEntity);

    ConfirmationTokenEntity getByToken(String token);

    void deleteToken(ConfirmationTokenEntity token);
}

