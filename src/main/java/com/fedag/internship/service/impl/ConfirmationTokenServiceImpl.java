package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.repository.ConfirmationTokenRepository;
import com.fedag.internship.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    @Value(("${token.minutes-to-expire}"))
    private long MINUTES_TO_EXPIRE;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Transactional
    @Override
    public ConfirmationTokenEntity createTokenForUser(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpires = LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE);
        ConfirmationTokenEntity tokenEntity = new ConfirmationTokenEntity(token, userEntity, tokenExpires);
        return confirmationTokenRepository.save(tokenEntity);
    }

    @Override
    public ConfirmationTokenEntity getByToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("ConfirmationToken", "token", token);
                });
    }

    @Override
    public void deleteToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.delete(token);
    }
}