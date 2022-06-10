package com.fedag.internship.ProposalCompanyServiceImpl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.impl.ProposalCompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.fedag.internship.domain.entity.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class ProposalCompanyServiceImpl_createProposalCompany
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_createProposalCompany {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;

    @Test
    public void testPositive() {
        ProposalCompanyEntity company = new ProposalCompanyEntity().setName("some name # 1");
        when(proposalCompanyRepository.save(company)).thenReturn(company);
        ProposalCompanyEntity result = proposalCompanyService.createProposalCompany(company);
        assertEquals("some name # 1", result.getName());
        assertEquals(NEW, result.getStatus());
        verify(proposalCompanyRepository, times(1)).save(any());
    }
}
