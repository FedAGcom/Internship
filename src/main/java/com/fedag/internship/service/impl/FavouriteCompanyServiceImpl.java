package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.CurrentUserService;
import com.fedag.internship.service.FavouriteCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavouriteCompanyServiceImpl implements FavouriteCompanyService {
    private final CurrentUserService currentUserService;
    private final CompanyService companyService;

    @Override
    public Page<CompanyEntity> getFavouriteCompanies(Pageable pageable) {
        log.info("Получение страницы избранных компаний пользователя");
        UserEntity userEntity = currentUserService.getCurrentUser();
        PageImpl<CompanyEntity> result = new PageImpl<>(userEntity.getFavouriteCompanies(),
                pageable,
                userEntity.getFavouriteCompanies().size());
        log.info("Страница избранных компаний пользователя получена");
        return result;
    }

    @Override
    @Transactional
    public UserEntity addFavouriteCompany(Long companyId) {
        log.info("Добавление компании с Id: {} в избранное пользователю", companyId);
        UserEntity userEntity = currentUserService.getCurrentUser();
        CompanyEntity companyEntity = companyService.findById(companyId);
        companyEntity.addFavouriteCompanyToUser(userEntity);
        log.info("Компания с Id: {} добавлена в избранное пользователю", companyId);
        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity removeFavouriteCompany(Long companyId) {
        log.info("Удаление компании с Id: {} из избранного у пользователя", companyId);
        UserEntity userEntity = currentUserService.getCurrentUser();
        CompanyEntity companyEntity = companyService.findById(companyId);
        companyEntity.removeFavouriteCompanyFromUser(userEntity);
        log.info("Компания с Id: {} удалена из избранного у пользователя", companyId);
        return userEntity;
    }
}
