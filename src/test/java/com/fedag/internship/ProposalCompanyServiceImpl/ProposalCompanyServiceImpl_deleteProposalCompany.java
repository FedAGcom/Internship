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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class ProposalCompanyServiceImpl_deleteProposalCompany
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_deleteProposalCompany {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;

    @Test
    public void testCommentNotFound() {
        Long id = anyLong();
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.empty());
        try {
            proposalCompanyService.deleteProposalCompany(id);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "ProposalCompany", "Id", id),
                    exception.getMessage());
            verify(proposalCompanyRepository, times(0)).deleteById(anyLong());
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        ProposalCompanyEntity company = new ProposalCompanyEntity()
                .setName("some name # 1")
                .setStatus(NEW);
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.of(company));
        proposalCompanyService.deleteProposalCompany(id);
        verify(proposalCompanyRepository, times(1)).deleteById(anyLong());
    }
}
