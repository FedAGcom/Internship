package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.UpdatingPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpdatingPasswordTokenRepository extends JpaRepository<UpdatingPasswordToken, Long> {
    Optional<UpdatingPasswordToken> findByToken(String token);
}
