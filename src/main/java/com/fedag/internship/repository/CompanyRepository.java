package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    @Modifying
    @Query("UPDATE CompanyEntity company SET company.active = false WHERE company.id = :id")
    void deleteById(Long id);

    Page<CompanyEntity> findAllByActive(Boolean active, Pageable pageable);
}
