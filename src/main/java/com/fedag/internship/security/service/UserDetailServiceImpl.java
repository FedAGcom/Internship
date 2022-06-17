package com.fedag.internship.security.service;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.getUserByEmail(email);
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                true,
                true,
                true,
                userEntity.getRole().getAuthorities());
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("%s with %s: %s not found", "User", "Email", email)));
    }
}
