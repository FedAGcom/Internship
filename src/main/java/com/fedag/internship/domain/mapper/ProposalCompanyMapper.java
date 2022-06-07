package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.ProposalCompanyRequest;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.ProposalCompanyResponse;
import com.fedag.internship.domain.entity.ProposalCompanyEntity;

/**
 * interface ProposalCompanyMapper for Dto layer and for class {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
public interface ProposalCompanyMapper {
    ProposalCompanyResponse toResponse(ProposalCompanyEntity proposalCompanyEntity);

    ProposalCompanyEntity fromRequest(ProposalCompanyRequest proposalCompanyRequest);

    ProposalCompanyEntity fromRequestUpdate(ProposalCompanyRequestUpdate proposalCompanyRequestUpdate);

    ProposalCompanyEntity merge(ProposalCompanyEntity source, ProposalCompanyEntity target);
}
