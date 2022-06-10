package com.fedag.internship.ProposalCompanyServiceImpl;

import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.ProposalCompanyMapper;
import com.fedag.internship.repository.ProposalCompanyRepository;
import com.fedag.internship.service.impl.ProposalCompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class ProposalCompanyServiceImpl_updateProposalCompany
 *
 * @author damir.iusupov
 * @since 2022-06-09
 */
@ExtendWith(MockitoExtension.class)
public class ProposalCompanyServiceImpl_updateProposalCompany {
    @InjectMocks
    private ProposalCompanyServiceImpl proposalCompanyService;

    @Mock
    private ProposalCompanyRepository proposalCompanyRepository;
    @Mock
    private ProposalCompanyMapper proposalCompanyMapper;

    @Test
    public void testCompanyNotFound() {
        Long id = anyLong();
        ProposalCompanyEntity company = new ProposalCompanyEntity();
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.empty());
        try {
            proposalCompanyService.updateProposalCompany(id, company);
        } catch (EntityNotFoundException exception) {
            assertEquals(String.format("%s with %s: %s not found", "ProposalCompany", "Id", id),
                    exception.getMessage());
            verify(proposalCompanyMapper, times(0))
                    .merge(any(ProposalCompanyEntity.class), any(ProposalCompanyEntity.class));
            verify(proposalCompanyRepository, times(0)).save(any(ProposalCompanyEntity.class));
        }
    }

    @Test
    public void testPositive() {
        Long id = anyLong();
        ProposalCompanyEntity oldCompany = new ProposalCompanyEntity().setName("some name");
        ProposalCompanyEntity newCompany = new ProposalCompanyEntity().setName("some name upd");
        when(proposalCompanyRepository.findById(id)).thenReturn(Optional.of(oldCompany));
        when(proposalCompanyMapper.merge(newCompany, oldCompany)).thenReturn(newCompany);
        when(proposalCompanyRepository.save(newCompany)).thenReturn(newCompany);
        ProposalCompanyEntity result = proposalCompanyService.updateProposalCompany(id, newCompany);
        assertEquals("some name upd", result.getName());
        verify(proposalCompanyMapper, times(1))
                .merge(any(ProposalCompanyEntity.class), any(ProposalCompanyEntity.class));
        verify(proposalCompanyRepository, times(1)).save(any(ProposalCompanyEntity.class));
    }
}
