package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.ProposalCompanyMapper;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.ProposalCompanyService;
import lombok.RequiredArgsConstructor;
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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProposalCompanyServiceImpl implements ProposalCompanyService {
    private final ProposalCompanyRepository proposalCompanyRepository;
    private final ProposalCompanyMapper proposalCompanyMapper;

    @Override
    public ProposalCompanyEntity getProposalCompanyById(Long id) {
        return proposalCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProposalCompany", "Id", id));
    }

    @Override
    public Page<ProposalCompanyEntity> getAllProposalCompanies(Pageable pageable) {
        return proposalCompanyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProposalCompanyEntity createProposalCompany(ProposalCompanyEntity proposalCompanyEntity) {
        proposalCompanyEntity.setStatus(NEW);
        return proposalCompanyRepository.save(proposalCompanyEntity);
    }

    @Override
    @Transactional
    public ProposalCompanyEntity updateProposalCompany(Long id, ProposalCompanyEntity source) {
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        ProposalCompanyEntity result = proposalCompanyMapper.merge(source, target);
        return proposalCompanyRepository.save(result);
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusUnderReview(Long id) {
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(UNDER_REVIEW);
        return proposalCompanyRepository.save(target);
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusRefused(Long id) {
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(REFUSED);
        return proposalCompanyRepository.save(target);
    }

    @Override
    @Transactional
    public ProposalCompanyEntity setProposalCompanyStatusApproved(Long id) {
        ProposalCompanyEntity target = this.getProposalCompanyById(id);
        target.setStatus(APPROVED);
        return proposalCompanyRepository.save(target);
    }


    @Override
    @Transactional
    public void deleteProposalCompany(Long id) {
        this.getProposalCompanyById(id);
        proposalCompanyRepository.deleteById(id);
    }
}
