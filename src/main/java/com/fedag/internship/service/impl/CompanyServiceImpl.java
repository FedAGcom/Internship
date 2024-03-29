package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.repository.CommentRepository;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.CompanyElasticSearchService;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyElasticSearchService companyElasticSearchService;
    private final CurrentUserService currentUserService;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CommentRepository commentRepository;

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
    public Page<CompanyEntity> findAllByActiveTrue(Pageable pageable) {
        log.info("Получение страницы с компаниями со статусом ACTIVE");
        Page<CompanyEntity> result = companyRepository.findAllByActive(true, pageable);
        log.info("Страница с компаниями со статусом ACTIVE получена");
        return result;
    }

    @Override
    public Page<CompanyEntity> findAllByActiveFalse(Pageable pageable) {
        log.info("Получение страницы с компаниями со статусом DELETED");
        Page<CompanyEntity> result = companyRepository.findAllByActive(false, pageable);
        log.info("Страница с компаниями со статусом DELETED получена");
        return result;
    }

    @Override
    @Transactional
    public CompanyEntity create(CompanyEntity companyEntity) {
        log.info("Создание компании");
        companyEntity.setActive(true);
        final UserEntity userEntity = currentUserService.getCurrentUser();
        userEntity.setCompany(companyEntity);
        companyEntity.setUser(userEntity);
        CompanyEntity result = companyRepository.save(companyEntity);
        companyElasticSearchService.save(companyEntity);
        log.info("Компания создана");
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

        log.info("Удаление комментариев компании с Id: {}", id);
        deleteComments(id);

        companyRepository.deleteById(id);
        log.info("Компания с Id: {} удалена", id);
    }

    @Override
    @Transactional
    public void deleteComments(Long id) {
        CompanyEntity companyEntity = this.findById(id);
        UserEntity userEntity = currentUserService.getCurrentUser();
        List<CommentEntity> commentEntities = companyEntity.getComments();
        for (int i = commentEntities.size() - 1; i >= 0; --i) {
            userEntity.removeComments(commentEntities.get(i));
            commentRepository.deleteById(commentEntities.get(i).getId());
            companyEntity.removeComments(commentEntities.get(i));
        }
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
