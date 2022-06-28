package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * class CurrentUserServiceImpl
 *
 * @author damir.iusupov
 * @since 2022-06-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    private final UserDetailServiceImpl userDetailService;

    @Override
    public UserEntity getCurrentUser() {
        log.info("Получение текущего пользователя из Security Context");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity result = userDetailService.getUserByEmail(auth.getName());
        log.info("Текущеий пользователь из Security Context получен");
        return result;
    }
}
