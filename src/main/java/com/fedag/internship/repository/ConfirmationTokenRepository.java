package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Long> {

    Optional<ConfirmationTokenEntity> findByToken(String token);

}
