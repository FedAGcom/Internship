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

import static com.fedag.internship.domain.entity.Status.APPROVED;
import static com.fedag.internship.domain.entity.Status.NEW;
import static com.fedag.internship.domain.entity.Status.REFUSED;
import static com.fedag.internship.domain.entity.Status.UNDER_REVIEW;

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
    public ProposalCompanyEntity getProposalCompanyById(Long id) {
        log.info("Получение заявки на создание компании c Id: {}", id);
        ProposalCompanyEntity result = proposalCompanyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Заявка на создание компании с Id: {} не найдена", id);
                    throw new EntityNotFoundException("ProposalCompany", "Id", id);
                });
        log.info("Заявка на создание компании c Id: {} получена", id);
        return result;
    }

    @Override
    public Page<ProposalCompanyEntity> getAllProposalCompanies(Pageable pageable) {
        log.info("Получение страницы с заявками на создание компании");
        Page<ProposalCompanyEntity> result = proposalCompanyRepository.findAll(pageable);
        log.info("Страница с заявками на создание компании получена");
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity createProposalCompany(ProposalCompanyEntity proposalCompanyEntity) {
        log.info("Создание заявки на создание компании");
        proposalCompanyEntity.setStatus(NEW);
        ProposalCompanyEntity result = proposalCompanyRepository.save(proposalCompanyEntity);
        log.info("Заявка на создание компании создана");
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity updateProposalCompany(Long id, ProposalCompanyEntity source) {
        log.info("Обновление заявки на создание компании c Id: {}", id);
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        ProposalCompanyEntity update = proposalCompanyMapper.merge(source, target);
        ProposalCompanyEntity result = proposalCompanyRepository.save(update);
        log.info("Заявка на создание компании c Id: {} обновлена", id);
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusUnderReview(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, UNDER_REVIEW.name());
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(UNDER_REVIEW);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusRefused(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, REFUSED.name());
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(REFUSED);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusApproved(Long id) {
        log.info("Обновление статуса заявки на создание компании c Id: {} на статус {}", id, APPROVED.name());
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(APPROVED);
        ProposalCompanyEntity result = proposalCompanyRepository.save(target);
        log.info("Статус заявки на создание компании c Id: {} изменен на статус {}", id, result.getStatus().name());
        return result;
    }


    @Override
    @Transactional
    public void deleteProposalCompany(Long id) {
        log.info("Удаление заявки на создание компании с Id: {}", id);
        this.getProposalCompanyById(id);
        proposalCompanyRepository.deleteById(id);
        log.info("Заявка на создание компании с Id: {} удалена", id);
    }
}
