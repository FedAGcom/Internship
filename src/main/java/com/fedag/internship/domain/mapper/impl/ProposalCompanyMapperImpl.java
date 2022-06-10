package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequest;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.ProposalCompanyResponse;
import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.mapper.ProposalCompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * class ProposalCompanyMapperImpl is implementation of {@link ProposalCompanyMapper} interface.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@Component
@RequiredArgsConstructor
public class ProposalCompanyMapperImpl implements ProposalCompanyMapper {
    private final ObjectMapper objectMapper;

    @Override
    public ProposalCompanyResponse toResponse(ProposalCompanyEntity proposalCompanyEntity) {
        return new ProposalCompanyResponse()
                .setId(proposalCompanyEntity.getId())
                .setName(proposalCompanyEntity.getName())
                .setStatus(proposalCompanyEntity.getStatus())
                .setDescription(proposalCompanyEntity.getDescription());
    }

    @Override
    public ProposalCompanyEntity fromRequest(ProposalCompanyRequest source) {
        return objectMapper.convertValue(source, ProposalCompanyEntity.class);
    }

    @Override
    public ProposalCompanyEntity fromRequestUpdate(ProposalCompanyRequestUpdate source) {
        return objectMapper.convertValue(source, ProposalCompanyEntity.class);
    }

    @Override
    public ProposalCompanyEntity merge(ProposalCompanyEntity source, ProposalCompanyEntity target) {
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        return target;
    }
}
