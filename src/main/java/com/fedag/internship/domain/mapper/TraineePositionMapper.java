package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.TraineePositionRequest;
import com.fedag.internship.domain.dto.TraineePositionResponse;
import com.fedag.internship.domain.dto.TraineePositionUpdate;
import com.fedag.internship.domain.entity.TraineePositionEntity;

public interface TraineePositionMapper {
    TraineePositionResponse toResponse(TraineePositionEntity positionEntity);

    TraineePositionEntity fromRequest(TraineePositionRequest userRequest);

    TraineePositionEntity fromRequestUpdate(TraineePositionUpdate positionUpdate);

    TraineePositionEntity merge(TraineePositionEntity source, TraineePositionEntity target);
}
