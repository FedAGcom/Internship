package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.ProposalCompanyMapper;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.ProposalCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fedag.internship.domain.entity.ProposalCompanyStatus.APPROVED;
import static com.fedag.internship.domain.entity.ProposalCompanyStatus.NEW;
import static com.fedag.internship.domain.entity.ProposalCompanyStatus.REFUSED;
import static com.fedag.internship.domain.entity.ProposalCompanyStatus.UNDER_REVIEW;

/**
 * class ProposalCompanyServiceImpl for create connections between
 * ProposalCompanyRepository and ProposalCompanyController.
 * Implementation of {@link ProposalCompanyService} interface.
 * Wrapper for {@link ProposalCompanyRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProposalCompanyServiceImpl implements ProposalCompanyService {
    private final ProposalCompanyRepository proposalCompanyRepository;
    private final ProposalCompanyMapper proposalCompanyMapper;

    @Override
    public ProposalCompanyEntity findById(Long id) {
        log.info("Получение заявки на создание компании c Id: {}", id);
        ProposalCompanyEntity result = proposalCompanyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Заявка на создание компании с Id: {} не найдена", id);
                    throw new EntityNotFoundException("ProposalCompany", "Id", id);
                });
        log.info("Заявка на создание компании c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<ProposalCompanyEntity> findAll(Pageable pageable) {
        log.info("Получение страницы с заявками на создание компании");
        Page<ProposalCompanyEntity> result = proposalCompanyRepository.findAll(pageable);
        log.info("Страница с заявками на создание компании получена");
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity create(ProposalCompanyEntity proposalCompanyEntity) {
        log.info("Создание заявки на создание компании");
        proposalCompanyEntity.setStatus(NEW);
        ProposalCompanyEntity result = proposalCompanyRepository.save(proposalCompanyEntity);
        log.info("Заявка на создание компании создана");
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity update(Long id, ProposalCompanyEntity source) {
        log.info("Обновление заявки на создание компании c Id: {}", id);
        ProposalCompanyEntity target = this.findById(id);
        ProposalCompanyEntity update = proposalCompanyMapper.merge(source, target);
        ProposalCompanyEntity result = proposalCompanyRepository.save(update);
        log.info("Заявка на создание компании c Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setStatusUnderReview(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, UNDER_REVIEW.name());
        ProposalCompanyEntity target = this.findById(id);
        target.setStatus(UNDER_REVIEW);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setStatusRefused(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, REFUSED.name());
        ProposalCompanyEntity target = this.findById(id);
        target.setStatus(REFUSED);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setStatusApproved(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, APPROVED.name());
        ProposalCompanyEntity target = this.findById(id);
        target.setStatus(APPROVED);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Удаление заявки на создание компании с Id: {}", id);
        this.findById(id);
        proposalCompanyRepository.deleteById(id);
        log.info("Заявка на создание компании с Id: {} удалена", id);
    }
}
