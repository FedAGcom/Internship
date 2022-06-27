package com.fedag.internship.repository;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TraineePositionRepository extends JpaRepository<TraineePositionEntity, Long> {
    @Modifying
    @Query("UPDATE TraineePositionEntity position SET position.active = false WHERE position.id = :id")
    void deleteById(Long id);

    Page<TraineePositionEntity> findAllByActive(Boolean active, Pageable pageable);
}
