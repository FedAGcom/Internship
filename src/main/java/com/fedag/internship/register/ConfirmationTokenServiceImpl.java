package com.fedag.internship.register;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
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
    private final Long MINUTES_TO_EXPIRE;
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
                    throw new EntityNotFoundException("ConfirmationToken", "Token", token);
                });
    }

    @Override
    public void deleteToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.delete(token);
    }
}
