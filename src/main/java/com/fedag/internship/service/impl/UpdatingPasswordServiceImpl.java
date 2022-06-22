package com.fedag.internship.service.impl;


import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidConfirmationTokenException;
import com.fedag.internship.domain.exception.InvalidOldPasswordException;
import com.fedag.internship.security.service.UserDetailServiceImpl;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.UpdatingPasswordService;
import com.fedag.internship.service.UpdatingPasswordTokenService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class UpdatingPasswordServiceImpl implements UpdatingPasswordService {
    private static final String EMAIL_SUBJECT = "Смена пароля";
    private static final String LINK = "http://localhost:8080/intership/api/v1.0/change-password/confirm?token=";

    private final UserDetailServiceImpl userDetailService;
    private final UpdatingPasswordTokenService updatingPasswordTokenService;
    private final EmailSenderService emailSenderService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changeUserPassword(String newPassword, String oldPassword) {
        UserEntity userEntity = userDetailService
                .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            String password = passwordEncoder.encode(newPassword);
            UpdatingPasswordToken token = updatingPasswordTokenService.createTokenForUser(userEntity, password);
            String head = String.format("<h1>Приветствуем вас, %s</h1>", userEntity.getEmail());
            String div1 = "<div>Добро пожаловать в FedAG Intership!</div>";
            String div2 = "<div>Для подтверждения смены пароля пройдите по ссылке ниже.</div><br>";
            String linkWithToken = LINK + token.getToken();
            String button = String.format("<a href=\"%s\">Activate link</a>", linkWithToken);
            String resultMessage = head + div1 + div2 + button;
            emailSenderService.send(userEntity.getEmail(), EMAIL_SUBJECT, resultMessage);
        } else {
            throw new InvalidOldPasswordException("Invalid password");
        }
    }

    @Override
    public UserEntity confirm(String token) {
        UpdatingPasswordToken updatingPasswordToken = updatingPasswordTokenService.getByToken(token);
        LocalDateTime tokenExpiredDate = updatingPasswordToken.getExpired();
        if (tokenExpiredDate.isBefore(now())) {
            updatingPasswordTokenService.deleteToken(updatingPasswordToken);
            throw new InvalidConfirmationTokenException("Token is expired");
        }
        UserEntity result = updatingPasswordToken.getUserEntity();
        result.setPassword(updatingPasswordToken.getPassword());
        updatingPasswordTokenService.deleteToken(updatingPasswordToken);
        return result;
    }

}
