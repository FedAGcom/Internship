package com.fedag.internship.security.service;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getUserByEmail(email);
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                true,
                true,
                true,
                userEntity.getRole().getAuthorities());
    }
}
