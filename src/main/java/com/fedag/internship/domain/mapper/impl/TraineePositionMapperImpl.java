package com.fedag.internship.domain.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.internship.domain.dto.request.TraineePositionRequest;
import com.fedag.internship.domain.dto.request.TraineePositionRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminTraineePositionResponse;
import com.fedag.internship.domain.dto.response.user.TraineePositionResponse;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraineePositionMapperImpl implements TraineePositionMapper {
    private final ObjectMapper objectMapper;

    @Override
    public TraineePositionResponse toResponse(TraineePositionEntity positionEntity) {
        return new TraineePositionResponse()
                .setId(positionEntity.getId())
                .setCompanyId(positionEntity.getCompany().getId())
                .setName(positionEntity.getName())
                .setDate(positionEntity.getDate())
                .setEmployeePosition(positionEntity.getEmployeePosition())
                .setDocuments(positionEntity.getDocuments())
                .setLocation(positionEntity.getLocation())
                .setStatus(positionEntity.getStatus())
                .setUrl(positionEntity.getUrl())
                .setText(positionEntity.getText());
    }

    @Override
    public AdminTraineePositionResponse toAdminResponse(TraineePositionEntity positionEntity) {
        return new AdminTraineePositionResponse()
                .setId(positionEntity.getId())
                .setCompanyId(positionEntity.getCompany().getId())
                .setName(positionEntity.getName())
                .setDate(positionEntity.getDate())
                .setEmployeePosition(positionEntity.getEmployeePosition())
                .setDocuments(positionEntity.getDocuments())
                .setLocation(positionEntity.getLocation())
                .setStatus(positionEntity.getStatus())
                .setUrl(positionEntity.getUrl())
                .setText(positionEntity.getText());
    }

    @Override
    public TraineePositionEntity fromRequest(TraineePositionRequest positionRequest) {
        return objectMapper.convertValue(positionRequest, TraineePositionEntity.class);
    }

    @Override
    public TraineePositionEntity fromRequestUpdate(TraineePositionRequestUpdate positionUpdate) {
        return objectMapper.convertValue(positionUpdate, TraineePositionEntity.class);
    }

    @Override
    public TraineePositionEntity merge(TraineePositionEntity source, TraineePositionEntity target) {
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getDate() != null) {
            target.setDate(source.getDate());
        }
        if (source.getEmployeePosition() != null) {
            target.setEmployeePosition(source.getEmployeePosition());
        }
        if (source.getDocuments() != null) {
            target.setDocuments(source.getDocuments());
        }
        if (source.getLocation() != null) {
            target.setLocation(source.getLocation());
        }
        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getUrl() != null) {
            target.setUrl(source.getUrl());
        }
        if (source.getText() != null) {
            target.setText(source.getText());
        }
        return target;
    }
}
