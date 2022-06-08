package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.TraineePositionRequest;
import com.fedag.internship.domain.dto.request.TraineePositionRequestUpdate;
import com.fedag.internship.domain.dto.response.TraineePositionResponse;
import com.fedag.internship.domain.entity.TraineePositionEntity;

public interface TraineePositionMapper {
    TraineePositionResponse toResponse(TraineePositionEntity positionEntity);

    TraineePositionEntity fromRequest(TraineePositionRequest userRequest);

    TraineePositionEntity fromRequestUpdate(TraineePositionRequestUpdate positionUpdate);

    TraineePositionEntity merge(TraineePositionEntity source, TraineePositionEntity target);
}
