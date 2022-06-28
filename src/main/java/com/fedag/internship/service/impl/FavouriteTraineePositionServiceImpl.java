package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.FavouriteTraineePositionService;
import com.fedag.internship.service.TraineePositionService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavouriteTraineePositionServiceImpl implements FavouriteTraineePositionService {
    private final TraineePositionService traineePositionService;
    private final UserService userService;

    @Override
    public Page<TraineePositionEntity> getFavouriteTraineePositions(Long userId, Pageable pageable) {
        log.info("Получение страницы избранных стажировок пользователя с Id: {}", userId);
        UserEntity userEntity = userService.findById(userId);
        PageImpl<TraineePositionEntity> result = new PageImpl<>(userEntity.getFavouriteTraineePositions(),
                pageable,
                userEntity.getFavouriteTraineePositions().size());
        log.info("Страница избранных стажировок пользователя с Id: {} получена", userId);
        return result;
    }

    @Override
    @Transactional
    public UserEntity addFavouriteTraineePosition(Long userId, Long traineeId) {
        log.info("Добавление стажировки с Id: {} в избранное пользователю с Id: {}", traineeId, userId);
        UserEntity userEntity = userService.findById(userId);
        TraineePositionEntity traineePositionEntity = traineePositionService.findById(traineeId);
        traineePositionEntity.addFavouriteTraineePositionToUser(userEntity);
        log.info("Стажировка с Id: {} добавлена в избранное пользователю с Id: {}", traineeId, userId);
        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity removeFavouriteTraineePosition(Long userId, Long traineeId) {
        log.info("Удаление стажировки с Id: {} из избранного у пользователя с Id: {}", traineeId, userId);
        UserEntity userEntity = userService.findById(userId);
        TraineePositionEntity traineePositionEntity = traineePositionService.findById(traineeId);
        traineePositionEntity.removeFavouriteTraineePositionFromUser(userEntity);
        log.info("Стажировка с Id: {} удалена из избранного у пользователя с Id: {}", traineeId, userId);
        return userEntity;
    }
}
