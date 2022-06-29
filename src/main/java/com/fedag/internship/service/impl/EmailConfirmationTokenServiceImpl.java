package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.ConfirmationTokenRepository;
import com.fedag.internship.service.EmailConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailConfirmationTokenServiceImpl implements EmailConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${token.minutes-to-expire}")
    private long MINUTES_TO_EXPIRE;

    @Transactional
    @Override
    public ConfirmationTokenEntity create(UserEntity userEntity) {
        log.info("Создание сущности с токеном для подтвеждения email пользователя");
        String token = generateToken();
        LocalDateTime tokenExpires = LocalDateTime.now().plusMinutes(MINUTES_TO_EXPIRE);
        ConfirmationTokenEntity tokenEntity = new ConfirmationTokenEntity(token, userEntity, tokenExpires);
        ConfirmationTokenEntity result = confirmationTokenRepository.save(tokenEntity);
        log.info("Сущность с токеном для подтвеждения email пользователя создана");
        return result;
    }

    @Override
    public ConfirmationTokenEntity findByToken(String token) {
        log.info("Получение сущности ConfirmationTokenEntity c токеном: {}", token);
        ConfirmationTokenEntity result = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Сущность ConfirmationTokenEntity c токеном: {} не найдена", token);
                    throw new EntityNotFoundException("ConfirmationToken", "token", token);
                });
        log.info("Сущность ConfirmationTokenEntity c токеном: {} получена", token);
        return result;
    }

    @Override
    public void delete(ConfirmationTokenEntity token) {
        log.info("Удаление сущности ConfirmationTokenEntity c токеном: {}", token);
        confirmationTokenRepository.delete(token);
        log.info("Сущность ConfirmationTokenEntity c токеном: {} удалена", token);
    }
    public String generateToken() {

        String token = "";
        for (int i = 0; i < 6; i++) {
            int number = new Random().nextInt(10);
            token += number;
        }
        return  token;
    }
}
