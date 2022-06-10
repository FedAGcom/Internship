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
 * class ProposalCompanyServiceImpl_getProposalCompanyById
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_getProposalCompanyById {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;

    @Test
    public void testCompanyNotFound() {
        Long id = anyLong();
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.empty());
        try {
            proposalCompanyService.getProposalCompanyById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "ProposalCompany", "Id", id),
                    exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        ProposalCompanyEntity company = new ProposalCompanyEntity()
                .setName("some name # 1")
                .setStatus(NEW);
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.of(company));
        ProposalCompanyEntity result = proposalCompanyService.getProposalCompanyById(id);
        assertEquals("some name # 1", result.getName());
        assertEquals(NEW, result.getStatus());
        verify(proposalCompanyRepository, times(1)).findById(anyLong());
    }
}
