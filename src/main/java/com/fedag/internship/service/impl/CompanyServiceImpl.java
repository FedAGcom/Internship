package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.EntityNotFoundException;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.repository.CompanyRepository;
import com.fedag.internship.service.CompanyService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserService userService;

    @Override
    public CompanyEntity getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company", "Id", id));
    }

    @Override
    public Page<CompanyEntity> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public CompanyEntity createCompany(Long userId, CompanyEntity companyEntity) {
        final UserEntity userEntity = userService.getUserById(userId);
        userEntity.setCompany(companyEntity);
        companyEntity.setUser(userEntity);
        return companyRepository.save(companyEntity);
    }

    @Override
    @Transactional
    public CompanyEntity updateCompany(Long id, CompanyEntity companyEntity) {
        CompanyEntity target = this.getCompanyById(id);
        CompanyEntity result = companyMapper.merge(companyEntity, target);
        return companyRepository.save(result);
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        this.getCompanyById(id);
        companyRepository.deleteById(id);
    }

    @Override
    public Page<CompanyDto> searchCompanyByName(String keyword, Pageable pageable) {
        return companyRepository.search(keyword, "name", pageable)
                .map(companyMapper::toDto);
    }
}
