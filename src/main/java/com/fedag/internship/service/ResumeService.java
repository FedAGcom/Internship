package com.fedag.internship.service;

import com.fedag.internship.domain.entity.ResumeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeService {
    ResumeEntity findById(Long id);

    Page<ResumeEntity> findAll(Pageable pageable);

    ResumeEntity create(ResumeEntity resumeEntity);

    ResumeEntity update(Long id, ResumeEntity resumeEntity);

    void deleteById(Long id);
}
