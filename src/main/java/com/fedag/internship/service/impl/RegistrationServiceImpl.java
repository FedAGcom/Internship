package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.request.RegistrationRequest;
import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidConfirmationTokenException;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.EmailConfirmationTokenService;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.RegistrationService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.fedag.internship.domain.entity.Role.USER;
import static com.fedag.internship.domain.util.UrlConstants.CONFIRM_URL;
import static com.fedag.internship.domain.util.UrlConstants.HOST_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.PORT;
import static com.fedag.internship.domain.util.UrlConstants.REGISTER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final EmailConfirmationTokenService emailConfirmationTokenService;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;

    private final static String EMAIL_SUBJECT = "Подтверждение аккаунта";
    private final static String LINK = "http://" +
            HOST_URL + ":" + PORT +
            MAIN_URL +
            VERSION +
            REGISTER_URL +
            CONFIRM_URL +
            "?token=";

    @Transactional
    @Override
    public void register(RegistrationRequest request) {
        log.info("Создание запроса на регистрацию пользователя с email: {}", request.getEmail());
        UserEntity userEntity = userMapper.fromRegistrationRequest(request);
        userEntity.setRole(USER);
        userEntity.setEnabled(false);
        userService.create(userEntity);
        ConfirmationTokenEntity token = emailConfirmationTokenService.create(userEntity);
        String head = String.format("<h3>Приветствуем вас, %s</h3>", userEntity.getEmail());
        String div1 = "<div>Добро пожаловать в FedAG Intership!</div>";
        String div2 = "<div>Для активации аккаунта пройдите по ссылке ниже.</div><br>";
        String linkWithToken = LINK + token.getToken();
        String button = String.format("<a href=\"%s\">Activate link</a>", linkWithToken);
        String resultMessage = head + div1 + div2 + button;
        emailSenderService.sendHtml(userEntity.getEmail(), EMAIL_SUBJECT, resultMessage);
        log.info("Запрос на регистрацию пользователя с email: {} создан", request.getEmail());
    }

    @Transactional
    @Override
    public void confirm(String token) {
        log.info("Подтверждение email через токен");
        ConfirmationTokenEntity confirmationToken = emailConfirmationTokenService.findByToken(token);
        LocalDateTime tokenExpiredDate = confirmationToken.getExpired();
        if (tokenExpiredDate.isBefore(now())) {
            log.error("Время действия токена истекло");
            userService.blockById(confirmationToken.getUserEntity().getId());
            emailConfirmationTokenService.delete(confirmationToken);
            throw new InvalidConfirmationTokenException("Token is expired");
        }
        confirmationToken.getUserEntity().setEnabled(true);
        emailConfirmationTokenService.delete(confirmationToken);
        log.info("Подтверждение email через токен пройдено");
    }
}
