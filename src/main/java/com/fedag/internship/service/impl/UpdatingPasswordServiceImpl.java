package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.service.ConfirmationTokenService;
import com.fedag.internship.service.EmailSenderService;
import com.fedag.internship.service.UpdatingPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatingPasswordServiceImpl implements UpdatingPasswordService {
    private static final String EMAIL_SUBJECT = "Подтверждение аккаунта";
    private final static String LINK = "http://localhost:8080/change-password/confirm?token=";

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UserEntity changeUserPassword(UserEntity userEntity, String password) {
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        UserEntity result = userRepository.save(userEntity);
        ConfirmationTokenEntity token = confirmationTokenService.createTokenForUser(userEntity);
        String text = LINK + token.getToken();
        emailSenderService.send(userEntity.getEmail(), EMAIL_SUBJECT, text);
        return result;
    }

    @Override
    public boolean checkIfValidOldPassword(UserEntity userEntity, String oldPassword) {
        return passwordEncoder.matches(oldPassword, userEntity.getPassword());
    }
}
