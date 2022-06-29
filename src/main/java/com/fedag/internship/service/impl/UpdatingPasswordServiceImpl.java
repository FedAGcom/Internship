package com.fedag.internship.service.impl;


import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidConfirmationTokenException;
import com.fedag.internship.domain.exception.InvalidOldPasswordException;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.UpdatingPasswordService;
import com.fedag.internship.service.UpdatingPasswordTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.fedag.internship.domain.util.UrlConstants.CHANGE_PWD_URL;
import static com.fedag.internship.domain.util.UrlConstants.CONFIRM_URL;
import static com.fedag.internship.domain.util.UrlConstants.HOST_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.PORT;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatingPasswordServiceImpl implements UpdatingPasswordService {
    private static final String EMAIL_SUBJECT = "Смена пароля";
    private static final String LINK = "http://" +
            HOST_URL + ":" + PORT +
            MAIN_URL +
            VERSION +
            USER_URL +
            CHANGE_PWD_URL +
            CONFIRM_URL +
            "?token=";

    private final UserDetailServiceImpl userDetailService;
    private final UpdatingPasswordTokenService updatingPasswordTokenService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changeUserPassword(String newPassword, String oldPassword) {
        log.info("Получение пользователя из контекста Security");
        UserEntity userEntity = userDetailService
                .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        log.info("Пользователь из контекста Security получен");
        log.info("Создание запроса на изменение пароля пользователя с email: {}", userEntity.getEmail());
        if (passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            String password = passwordEncoder.encode(newPassword);
            UpdatingPasswordToken token = updatingPasswordTokenService.create(userEntity, password);
            String head = String.format("<h3>Приветствуем вас, %s</h3>", userEntity.getEmail());
            String div1 = "<div>Добро пожаловать в FedAG Intership!</div>";
            String div2 = "<div>Для подтверждения смены пароля пройдите по ссылке ниже.</div><br>";
            String linkWithToken = LINK + token.getToken();
            String button = String.format("<a href=\"%s\">Activate link</a>", linkWithToken);
            String resultMessage = head + div1 + div2 + button;
            emailSenderService.sendHtml(userEntity.getEmail(), EMAIL_SUBJECT, resultMessage);
            log.info("Запрос на изменение пароля пользователя с email: {} создан", userEntity.getEmail());
        } else {
            log.error("Запрос на изменение пароля пользователя с email: {} отклонен " +
                    "(старый пароль введен неверно)", userEntity.getEmail());
            throw new InvalidOldPasswordException("Invalid password");
        }
    }

    @Override
    public UserEntity confirm(String token) {
        log.info("Подтверждение изменения пароля через токен");
        UpdatingPasswordToken updatingPasswordToken = updatingPasswordTokenService.findByToken(token);
        LocalDateTime tokenExpiredDate = updatingPasswordToken.getExpired();
        if (tokenExpiredDate.isBefore(now())) {
            log.error("Время действия токена истекло");
            updatingPasswordTokenService.delete(updatingPasswordToken);
            throw new InvalidConfirmationTokenException("Token is expired");
        }
        UserEntity result = updatingPasswordToken.getUserEntity();
        result.setPassword(updatingPasswordToken.getPassword());
        updatingPasswordTokenService.delete(updatingPasswordToken);
        log.info("Подтверждение изменения пароля через токен пройдено");
        return result;
    }
}
