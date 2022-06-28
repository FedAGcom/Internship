package com.fedag.internship.service;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TraineePositionService {
    TraineePositionEntity findById(Long id);

    Page<TraineePositionEntity> findAll(Pageable pageable);

    TraineePositionEntity create(TraineePositionEntity positionEntity);

    TraineePositionEntity update(Long id, TraineePositionEntity positionEntity);

    void deleteById(Long id);

    Page<TraineePositionEntity> searchByCompany(String keyword, Pageable pageable);
}
