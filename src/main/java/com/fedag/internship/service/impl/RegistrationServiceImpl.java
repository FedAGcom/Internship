package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.request.RegistrationRequest;
import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.Role;
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
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;

    private final static String EMAIL_SUBJECT = "Подтверждение аккаунта";
    private final static String LINK = "http://localhost:8080/intership/api/v1.0/registration/confirm?token=";

    @Transactional
    @Override
    public void register(RegistrationRequest request) {
        UserEntity userEntity = userMapper.fromRegistrationRequest(request);
        userEntity.setRole(Role.USER);
        userEntity.setEnabled(false);
        userService.createUser(userEntity);
        ConfirmationTokenEntity token = confirmationTokenService.createTokenForUser(userEntity);
        String head = String.format("<h1>Приветствуем вас, %s</h1>", userEntity.getEmail());
        String div1 = "<div>Добро пожаловать в FedAG Intership!</div>";
        String div2 = "<div>Для активации аккаунта пройдите по ссылке ниже.</div><br>";
        String linkWithToken = LINK + token.getToken();
        String button = String.format("<a href=\"%s\">Activate link</a>", linkWithToken);
        String resultMessage = head + div1 + div2 + button;
        emailSenderService.send(userEntity.getEmail(), EMAIL_SUBJECT, resultMessage);
    }

    @Transactional
    @Override
    public void confirm(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
        LocalDateTime tokenExpiredDate = confirmationToken.getExpired();
        if (tokenExpiredDate.isBefore(now())) {
            userService.deleteUser(confirmationToken.getUserEntity().getId());
            throw new InvalidConfirmationTokenException("Token is expired");
        }
        confirmationToken.getUserEntity().setEnabled(true);
        confirmationTokenService.deleteToken(confirmationToken);
    }
}
