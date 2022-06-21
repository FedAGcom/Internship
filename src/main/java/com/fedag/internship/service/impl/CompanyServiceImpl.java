package com.fedag.internship.service.impl;

import com.fedag.internship.domain.document.CompanyElasticSearchEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.CompanyElasticSearchService;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserService userService;
    private final CompanyElasticSearchService companyElasticSearchService;

    @Override
    public CompanyEntity getCompanyById(Long id) {
        log.info("Получение компании c Id: {}", id);
        CompanyEntity result = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Компания с Id: {} не найдена", id);
                    throw new EntityNotFoundException("Company", "Id", id);
                });
        log.info("Компания c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<CompanyEntity> getAllCompanies(Pageable pageable) {
        log.info("Получение страницы с компаниями");
        Page<CompanyEntity> result = companyRepository.findAll(pageable);
        log.info("Страница с компаниями получена");
        return result;
    }

    @Override
    @Transactional
    public CompanyEntity createCompany(Long userId, CompanyEntity companyEntity) {
        log.info("Создание компании от пользователя с Id: {}", userId);
        final UserEntity userEntity = userService.getUserById(userId);
        userEntity.setCompany(companyEntity);
        companyEntity.setUser(userEntity);
        CompanyEntity result = companyRepository.save(companyEntity);
        companyElasticSearchService.saveCompany(companyEntity);
        log.info("Компания от пользователя с Id: {} создана", userId);
        return result;
    }

    @Override
    @Transactional
    public CompanyEntity updateCompany(Long id, CompanyEntity companyEntity) {
        log.info("Обновление компании с Id: {}", id);
        CompanyEntity target = this.getCompanyById(id);
        CompanyEntity update = companyMapper.merge(companyEntity, target);
        CompanyEntity result = companyRepository.save(update);
        log.info("Компания с Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        log.info("Удаление компании с Id: {}", id);
        this.getCompanyById(id);
        companyRepository.deleteById(id);
        log.info("Компания с Id: {} удалена", id);
    }

    @Override
    @Transactional
    public Page<CompanyEntity> searchCompanyByName(String keyword, Pageable pageable) {
        log.info("Получение страницы с компаниями по имени");
        Page<CompanyElasticSearchEntity> companiesFromElasticSearch = companyElasticSearchService.searchByName(pageable, keyword);
        Page<CompanyEntity> result = companiesFromElasticSearch.map(el -> this.getCompanyById(el.getCompanyEntityId()));
        log.info("Страница с компаниями по имени получена");
        return result;
    }
}
