package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Создание сущности UserDetails через пользователя с email: {}", email);
        UserEntity userEntity = this.getUserByEmail(email);
        User result = new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                true,
                true,
                true,
                userEntity.getRole().getAuthorities());
        log.info("Сущность UserDetails через пользователя с email: {} создана", email);
        return result;
    }

    public UserEntity getUserByEmail(String email) {
        log.info("Поиск пользователя с email: {}", email);
        UserEntity result = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.info("Пользователь с email: {} не найден", email);
                    throw new UsernameNotFoundException(
                            String.format("%s with %s: %s not found", "User", "Email", email));
                });
        log.info("Пользователь с email: {} найден", email);
        return result;
    }
}
