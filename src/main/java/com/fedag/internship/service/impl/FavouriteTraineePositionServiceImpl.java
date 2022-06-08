package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.FavouriteTraineePositionService;
import com.fedag.internship.service.TraineePositionService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavouriteTraineePositionServiceImpl implements FavouriteTraineePositionService {
    private final TraineePositionService traineePositionService;
    private final UserService userService;

    @Override
    public Page<TraineePositionEntity> getAllFavouriteTraineePositions(Long userId, Pageable pageable) {
        UserEntity userEntity = userService.getUserById(userId);
        return new PageImpl<>(userEntity.getFavouriteTraineePositions(),
                pageable,
                userEntity.getFavouriteTraineePositions().size());
    }

    @Override
    @Transactional
    public UserEntity addFavouriteTraineePosition(Long userId, Long traineeId) {
        UserEntity userEntity = userService.getUserById(userId);
        TraineePositionEntity traineePositionEntity = traineePositionService.getPositionById(traineeId);
        traineePositionEntity.addFavouriteTraineePositionToUser(userEntity);
        return userEntity;
    }

    @Override
    @Transactional
    public void deleteFavouriteTraineePosition(Long userId, Long traineeId) {
        UserEntity userEntity = userService.getUserById(userId);
        TraineePositionEntity traineePositionEntity = traineePositionService.getPositionById(traineeId);
        traineePositionEntity.removeFavouriteTraineePosition(userEntity);
    }
}
