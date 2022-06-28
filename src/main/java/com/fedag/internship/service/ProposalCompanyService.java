package com.fedag.internship.service;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface ProposalCompanyService for class {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
public interface ProposalCompanyService {
    ProposalCompanyEntity findById(Long id);

    Page<ProposalCompanyEntity> findAll(Pageable pageable);

    ProposalCompanyEntity create(ProposalCompanyEntity proposalCompanyEntity);

    ProposalCompanyEntity update(Long id, ProposalCompanyEntity source);

    ProposalCompanyEntity setStatusUnderReview(Long id);

    ProposalCompanyEntity setStatusRefused(Long id);

    ProposalCompanyEntity setStatusApproved(Long id);

    void deleteById(Long id);
}
