package com.fedag.internship.service;

import com.fedag.internship.domain.entity.CompanyEntity;
import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavouriteCompanyService {
    Page<CompanyEntity> getFavouriteCompanies(Pageable pageable);

    UserEntity addFavouriteCompany(Long companyId);

    UserEntity removeFavouriteCompany(Long companyId);
}
