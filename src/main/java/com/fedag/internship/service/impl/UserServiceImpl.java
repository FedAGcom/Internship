package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityAlreadyExistsException;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fedag.internship.domain.entity.Role.DELETED;
import static com.fedag.internship.domain.entity.Role.USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getUserById(Long id) {
        log.info("Получение пользователя c Id: {}", id);
        UserEntity result = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Пользователь с Id: {} не найден", id);
                    throw new EntityNotFoundException("User", "Id", id);
                });
        log.info("Пользователь c Id: {} получен", id);
        return result;
    }

    @Override
    public Page<UserEntity> getAllUsersWithRoleUser(Pageable pageable) {
        log.info("Получение страницы с пользователями с ролью USER");
        Page<UserEntity> result = userRepository.findAllByRole(USER, pageable);
        log.info("Страница с пользователями с ролью USER получена");
        return result;
    }

    @Override
    public Page<UserEntity> getAllUsersWithRoleDeleted(Pageable pageable) {
        log.info("Получение страницы с пользователями с ролью DELETED");
        Page<UserEntity> result = userRepository.findAllByRole(DELETED, pageable);
        log.info("Страница с пользователями с ролью DELETED получена");
        return result;
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        log.info("Создание пользователя");
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("User", "email", userEntity.getEmail());
        }
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        UserEntity result = userRepository.save(userEntity);
        log.info("Пользователь создан");
        return result;
    }

    @Override
    @Transactional
    public UserEntity updateUser(Long id, UserEntity userEntity) {
        log.info("Обновление пользователя с Id: {}", id);
        UserEntity target = this.getUserById(id);
        UserEntity update = userMapper.merge(userEntity, target);
        UserEntity result = userRepository.save(update);
        log.info("Пользователь с Id: {} обновлен", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Удаление пользователя с Id: {}", id);
        this.getUserById(id);
        userRepository.deleteById(id);
        log.info("Пользователь с Id: {} удален", id);
    }
}
