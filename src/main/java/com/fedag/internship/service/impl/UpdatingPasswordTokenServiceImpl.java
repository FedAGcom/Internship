package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.UpdatingPasswordTokenRepository;
import com.fedag.internship.service.UpdatingPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UpdatingPasswordTokenServiceImpl implements UpdatingPasswordTokenService {
    @Value("${token.minutes-to-expire}")
    private long MINUTES_TO_EXPIRE;

    private final UpdatingPasswordTokenRepository updatingPasswordTokenRepository;

    @Override
    public UpdatingPasswordToken createTokenForUser(UserEntity userEntity, String newPassword) {
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpires = LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE);
        UpdatingPasswordToken updatingPasswordToken = new UpdatingPasswordToken(token, userEntity, tokenExpires, newPassword);
        return updatingPasswordTokenRepository.save(updatingPasswordToken);
    }

    @Override
    public UpdatingPasswordToken getByToken(String token) {
        return updatingPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("UpdatingPasswordToken", "token", token);
                });
    }

    @Override
    public void deleteToken(UpdatingPasswordToken token) {
        updatingPasswordTokenRepository.delete(token);
    }
}
