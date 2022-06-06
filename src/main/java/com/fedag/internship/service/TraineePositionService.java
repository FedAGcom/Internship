package com.fedag.internship.service;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TraineePositionService {
    TraineePositionEntity getPositionById(Long id);

    Page<TraineePositionEntity> getAllPositions(Pageable pageable);

    TraineePositionEntity createPosition(Long userId, TraineePositionEntity positionEntity);

    TraineePositionEntity updatePosition(Long id, TraineePositionEntity positionEntity);

    void deletePosition(Long id);
}
