package com.fedag.internship.ProposalCompanyServiceImpl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.impl.ProposalCompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class ProposalCompanyServiceImpl_getAllProposalCompanies
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_getAllProposalCompanies {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;

    @Test
    public void testPositive() {
        ProposalCompanyEntity company1 = new ProposalCompanyEntity().setName("some name # 1");
        ProposalCompanyEntity company2 = new ProposalCompanyEntity().setName("some name # 2");
        ProposalCompanyEntity company3 = new ProposalCompanyEntity().setName("some name # 3");
        Page<ProposalCompanyEntity> page = new PageImpl<>(List.of(company1, company2, company3));
        when(proposalCompanyRepository.findAll(Pageable.ofSize(5))).thenReturn(page);
        Page<ProposalCompanyEntity> result = proposalCompanyService.getAllProposalCompanies(Pageable.ofSize(5));
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("some name # 1", result.getContent().get(0).getName());
        assertEquals("some name # 2", result.getContent().get(1).getName());
        assertEquals("some name # 3", result.getContent().get(2).getName());
        verify(proposalCompanyRepository, times(1)).findAll(any(Pageable.class));
    }
}
