package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.FavouriteCompanyService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavouriteCompanyServiceImpl implements FavouriteCompanyService {
    private final CompanyService companyService;
    private final UserService userService;

    @Override
    public Page<CompanyEntity> getAllFavouriteCompanies(Long userId, Pageable pageable) {
        UserEntity userEntity = userService.getUserById(userId);
        return new PageImpl<>(userEntity.getFavouriteCompanies(),
                pageable,
                userEntity.getFavouriteCompanies().size());
    }

    @Override
    @Transactional
    public UserEntity addFavouriteCompany(Long userId, Long companyId) {
        UserEntity userEntity = userService.getUserById(userId);
        CompanyEntity companyEntity = companyService.getCompanyById(companyId);
        companyEntity.addFavouriteCompanyToUser(userEntity);
        return userEntity;
    }

    @Override
    @Transactional
    public void deleteFavouriteCompany(Long userId, Long companyId) {
        UserEntity userEntity = userService.getUserById(userId);
        CompanyEntity companyEntity = companyService.getCompanyById(companyId);
        companyEntity.removeFavouriteCompany(userEntity);
    }
}
