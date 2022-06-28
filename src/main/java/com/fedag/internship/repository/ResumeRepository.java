package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.ResumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
}
