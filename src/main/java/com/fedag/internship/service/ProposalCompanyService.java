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
    ProposalCompanyEntity getProposalCompanyById(Long id);

    Page<ProposalCompanyEntity> getAllProposalCompanies(Pageable pageable);

    ProposalCompanyEntity createProposalCompany(ProposalCompanyEntity proposalCompanyEntity);

    ProposalCompanyEntity updateProposalCompany(Long id, ProposalCompanyEntity source);

    ProposalCompanyEntity setProposalCompanyStatusUnderReview(Long id);

    ProposalCompanyEntity setProposalCompanyStatusRefused(Long id);

    ProposalCompanyEntity setProposalCompanyStatusApproved(Long id);

    void deleteProposalCompany(Long id);
}
