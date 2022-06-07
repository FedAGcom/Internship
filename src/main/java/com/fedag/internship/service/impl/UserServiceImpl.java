package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.repository.UserRepository;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", "Id", id));
    }

    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public UserEntity updateUser(Long id, UserEntity userEntity) {
        UserEntity target = this.getUserById(id);
        UserEntity result = userMapper.merge(userEntity, target);
        return userRepository.save(result);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        this.getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<CompanyEntity> getAllFavouriteCompanies(Long id) {
        UserEntity userEntity = getUserById(id);
        return userEntity.getFavouriteCompanies();
    }

    @Override
    @Transactional
    public UserEntity addFavouriteCompany(Long id, CompanyEntity companyEntity) {
        UserEntity userEntity = getUserById(id);
        List<CompanyEntity> list = userEntity.getFavouriteCompanies();
        list.add(companyEntity);
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteFavouriteCompany(Long id, CompanyEntity companyEntity) {
        UserEntity userEntity = getUserById(id);
        List<CompanyEntity> list = userEntity.getFavouriteCompanies();
        list.remove(companyEntity);
    }
}
