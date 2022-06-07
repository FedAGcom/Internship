package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface ProposalCompanyRepository extends {@link JpaRepository} for class {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
public interface ProposalCompanyRepository extends JpaRepository<ProposalCompanyEntity, Long> {
}
