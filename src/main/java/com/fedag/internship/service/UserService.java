package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserEntity getUserById(Long id);

    Page<UserEntity> getAllUsers(Pageable pageable);

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(Long id, UserEntity user);

    void deleteUser(Long id);

    List<CompanyEntity> getAllFavouriteCompanies(Long id);

    UserEntity addFavouriteCompany(Long id, CompanyEntity companyEntity);

    void deleteFavouriteCompany(Long id, CompanyEntity companyEntity);
}
