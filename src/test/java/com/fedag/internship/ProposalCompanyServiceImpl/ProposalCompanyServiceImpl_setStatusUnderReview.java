package com.fedag.internship.ProposalCompanyServiceImpl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.impl.ProposalCompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fedag.internship.domain.entity.Status.NEW;
import static com.fedag.internship.domain.entity.Status.UNDER_REVIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class ProposalCompanyServiceImpl_setProposalCompanyStatusUnderReview
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_setStatusUnderReview {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;

    @Test
    public void testCompanyNotFound() {
        Long id = anyLong();
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.empty());
        try {
            proposalCompanyService.setStatusUnderReview(id);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "ProposalCompany", "Id", id),
                    exception.getMessage());
            verify(proposalCompanyRepository, times(0)).save(any(ProposalCompanyEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        ProposalCompanyEntity company = new ProposalCompanyEntity()
                .setName("some name")
                .setStatus(NEW);
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.of(company));
        when(proposalCompanyRepository.save(company)).thenReturn(company);
        ProposalCompanyEntity result = proposalCompanyService.setStatusUnderReview(id);
        assertEquals("some name", result.getName());
        assertEquals(UNDER_REVIEW, result.getStatus());
        verify(proposalCompanyRepository, times(1)).save(any(ProposalCompanyEntity.class));
    }
}
