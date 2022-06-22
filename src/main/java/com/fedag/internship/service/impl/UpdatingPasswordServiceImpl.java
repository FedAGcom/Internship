package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidOldPasswordException;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.security.service.UserDetailServiceImpl;
import com.fedag.internship.service.ConfirmationTokenService;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.UpdatingPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatingPasswordServiceImpl implements UpdatingPasswordService {
    private static final String EMAIL_SUBJECT = "Смена пароля";
    private static final String LINK = "http://localhost:8080/change-password/confirm?token=";

    private final UserDetailServiceImpl userDetailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity changeUserPassword(String newPassword, String oldPassword) {
        UserEntity userEntity = userDetailService
                .getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(encodedPassword);
            UserEntity result = userRepository.save(userEntity);
            ConfirmationTokenEntity token = confirmationTokenService.createTokenForUser(userEntity);
            String head = String.format("<h1>Приветствуем вас, %s</h1>", userEntity.getEmail());
            String div1 = "<div>Добро пожаловать в FedAG Intership!</div>";
            String div2 = "<div>Для подтверждения смены пароля пройдите по ссылке ниже.</div><br>";
            String linkWithToken = LINK + token.getToken();
            String button = String.format("<a href=\"%s\">Activate link</a>", linkWithToken);
            String resultMessage = head + div1 + div2 + button;
            emailSenderService.send(userEntity.getEmail(), EMAIL_SUBJECT, resultMessage);
            return result;
        } else {
            throw new InvalidOldPasswordException("Invalid password");
        }
    }
}
