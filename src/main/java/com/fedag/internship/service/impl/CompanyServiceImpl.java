package com.fedag.internship.service.impl;

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
    public CompanyEntity findById(Long id) {
        log.info("Получение компании c Id: {}", id);
        CompanyEntity result = companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Компания с Id: {} не найдена", id);
                    throw new EntityNotFoundException("Company", "Id", id);
                });
        log.info("Компания c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<CompanyEntity> findAll(Pageable pageable) {
        log.info("Получение страницы с компаниями");
        Page<CompanyEntity> result = companyRepository.findAll(pageable);
        log.info("Страница с компаниями получена");
        return result;
    }

    @Override
    @Transactional
    public CompanyEntity create(Long userId, CompanyEntity companyEntity) {
        log.info("Создание компании от пользователя с Id: {}", userId);
        final UserEntity userEntity = userService.findById(userId);
        userEntity.setCompany(companyEntity);
        companyEntity.setUser(userEntity);
        CompanyEntity result = companyRepository.save(companyEntity);
        companyElasticSearchService.save(companyEntity);
        log.info("Компания от пользователя с Id: {} создана", userId);
        return result;
    }

    @Override
    @Transactional
    public CompanyEntity update(Long id, CompanyEntity companyEntity) {
        log.info("Обновление компании с Id: {}", id);
        CompanyEntity target = this.findById(id);
        CompanyEntity update = companyMapper.merge(companyEntity, target);
        CompanyEntity result = companyRepository.save(update);
        log.info("Компания с Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Удаление компании с Id: {}", id);
        this.findById(id);
        companyRepository.deleteById(id);
        log.info("Компания с Id: {} удалена", id);
    }

    @Override
    @Transactional
    public Page<CompanyEntity> searchByName(String keyword, Pageable pageable) {
        log.info("Получение страниц с компаниями по имени");
        Page<CompanyEntity> result = companyElasticSearchService
                .elasticsearchByName(pageable, keyword)
                .map(el -> this.findById(el.getCompanyEntityId()));
        log.info("Страница с компаниями по имени получена");
        return result;
    }
}
