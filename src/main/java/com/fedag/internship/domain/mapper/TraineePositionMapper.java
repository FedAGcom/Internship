package com.fedag.internship.domain.mapper;

import com.fedag.internship.domain.dto.request.TraineePositionRequest;
import com.fedag.internship.domain.dto.request.TraineePositionRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminTraineePositionResponse;
import com.fedag.internship.domain.dto.response.user.TraineePositionResponse;
import com.fedag.internship.domain.entity.TraineePositionEntity;

public interface TraineePositionMapper {
    TraineePositionResponse toResponse(TraineePositionEntity positionEntity);

    AdminTraineePositionResponse toAdminResponse(TraineePositionEntity positionEntity);

    TraineePositionEntity fromRequest(TraineePositionRequest userRequest);

    TraineePositionEntity fromRequestUpdate(TraineePositionRequestUpdate positionUpdate);

    TraineePositionEntity merge(TraineePositionEntity source, TraineePositionEntity target);
}
