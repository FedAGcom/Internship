package com.fedag.internship.repository;


import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineePositionRepository extends JpaRepository<TraineePositionEntity, Long> {
}
