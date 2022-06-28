package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.UpdatingPasswordTokenRepository;
import com.fedag.internship.service.UpdatingPasswordTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatingPasswordTokenServiceImpl implements UpdatingPasswordTokenService {
    private final UpdatingPasswordTokenRepository updatingPasswordTokenRepository;

    @Value("${token.minutes-to-expire}")
    private long MINUTES_TO_EXPIRE;

    @Override
    public UpdatingPasswordToken create(UserEntity userEntity, String newPassword) {
        log.info("Создание сущности с токеном для изменения пароля пользователя {}", userEntity.getEmail());
        String token = UUID.randomUUID().toString();
        LocalDateTime tokenExpires = LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE);
        UpdatingPasswordToken updatingPasswordToken = new UpdatingPasswordToken(token, userEntity, tokenExpires, newPassword);
        UpdatingPasswordToken result = updatingPasswordTokenRepository.save(updatingPasswordToken);
        log.info("Сущность с токеном для изменения пароля пользователя {} создана", userEntity.getEmail());
        return result;
    }

    @Override
    public UpdatingPasswordToken findByToken(String token) {
        log.info("Получение сущности UpdatingPasswordToken c токеном: {}", token);
        UpdatingPasswordToken result = updatingPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Сущность UpdatingPasswordToken c токеном: {} не найдена", token);
                    throw new EntityNotFoundException("UpdatingPasswordToken", "token", token);
                });
        log.info("Сущность UpdatingPasswordToken c токеном: {} получена", token);
        return result;
    }

    @Override
    public void delete(UpdatingPasswordToken token) {
        log.info("Удаление сущности UpdatingPasswordToken c токеном: {}", token);
        updatingPasswordTokenRepository.delete(token);
        log.info("Сущность ConfirmationTokenEntity c токеном: {} удалена", token);
    }
}
