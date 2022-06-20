package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.Role;
import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity user SET user.role = 'DELETED' WHERE user.id = :id")
    void deleteById(Long id);

    Page<UserEntity> findAllByRole(Role role, Pageable pageable);
}
