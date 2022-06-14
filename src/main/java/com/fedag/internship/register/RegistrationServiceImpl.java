package com.fedag.internship.register;

import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;

    @Transactional
    @Override
    public void register(UserRequest request) {
        UserEntity userEntity = userMapper.fromRequest(request);
        userService.createUser(userEntity);
        confirmationTokenService.createTokenForUser(userEntity);
        emailSenderService.send(userEntity.getEmail(), "token");
    }

    @Transactional
    @Override
    public void confirm(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
        LocalDateTime tokenExpiredDate = confirmationToken.getExpiredAt();
        if (tokenExpiredDate.isBefore(now())) {
            userService.deleteUser(confirmationToken.getUserEntity().getId());
            throw new RuntimeException("token is expired");
        }
        confirmationToken.getUserEntity().setEnabled(true);
        confirmationTokenService.deleteToken(confirmationToken);
    }
}
