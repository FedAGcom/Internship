package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.request.RegistrationRequest;
import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidConfirmationTokenException;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.ConfirmationTokenService;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.RegistrationService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static final String EMAIL_SUBJECT = "Подтверждение аккаунта";
    private final static String LINK = "http://localhost:8080/registration/confirm?token=";

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;

    @Transactional
    @Override
    public void register(RegistrationRequest request) {
        UserEntity userEntity = userMapper.fromRegistrationRequest(request);
        userService.createUser(userEntity);
        ConfirmationTokenEntity token = confirmationTokenService.createTokenForUser(userEntity);
        String text = LINK + token.getToken();
        emailSenderService.send(userEntity.getEmail(), EMAIL_SUBJECT, text);
    }

    @Transactional
    @Override
    public void confirm(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
        LocalDateTime tokenExpiredDate = confirmationToken.getExpiredAt();
        if (tokenExpiredDate.isBefore(now())) {
            userService.deleteUser(confirmationToken.getUserEntity().getId());
            throw new InvalidConfirmationTokenException("Token is expired");
        }
        confirmationToken.getUserEntity().setEnabled(true);
        confirmationTokenService.deleteToken(confirmationToken);
    }

}
