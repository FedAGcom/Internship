package com.fedag.internship.service;

import com.fedag.internship.domain.entity.UserEntity;

/**
 * interface CurrentUserService
 *
 * @author damir.iusupov
 * @since 2022-06-28
 */
public interface CurrentUserService {
    UserEntity getCurrentUser();
}
