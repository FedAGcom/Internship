package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    public void getCompanyByIdTestWhenIdIsInvalid() {
        Long invalidId = -1L;
        when(companyRepository.findById(invalidId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> companyService.findById(invalidId));
    }

    @Test
    public void getCompanyByIdTestWhenIdIsValid() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(new CompanyEntity()));
        assertDoesNotThrow(() -> companyService.findById(anyLong()));
    }

    @Test
    public void getAllCompaniesTest() {
      Pageable pageable = any(Pageable.class);
      companyService.findAll(pageable);
      verify(companyRepository).findAll(pageable);
    }

    @Test
    public void deleteCompanyTest() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(new CompanyEntity()));

        companyService.deleteById(anyLong());

        verify(companyRepository).deleteById(anyLong());
        verify(companyRepository).findById(anyLong());
    }

    @Test
    public void createCompanyTest() {
        UserEntity userWithoutCompany = new UserEntity();
        CompanyEntity companyWithoutUser = new CompanyEntity();
        when(userService.findById(anyLong())).thenReturn(userWithoutCompany);

        companyService.create(anyLong(), companyWithoutUser);

        assertNotNull(userWithoutCompany.getCompany());
        assertNotNull(companyWithoutUser.getUser());
        verify(companyRepository).save(companyWithoutUser);
    }

    @Test
    public void updateCompanyTest() {
        CompanyEntity someCompanyEntity = new CompanyEntity();
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(someCompanyEntity));

        companyService.update(anyLong(), someCompanyEntity);

        verify(companyRepository).save(any());
        verify(companyMapper).merge(any(), any());
    }
}
