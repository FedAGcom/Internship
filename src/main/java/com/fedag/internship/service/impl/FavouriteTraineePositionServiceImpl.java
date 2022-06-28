package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.service.CurrentUserService;
import com.fedag.internship.service.FavouriteTraineePositionService;
import com.fedag.internship.service.TraineePositionService;
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
    private final CurrentUserService currentUserService;

    @Override
    public Page<TraineePositionEntity> getFavouriteTraineePositions(Pageable pageable) {
        log.info("Получение страницы избранных стажировок пользователя");
        UserEntity userEntity = currentUserService.getCurrentUser();
        PageImpl<TraineePositionEntity> result = new PageImpl<>(userEntity.getFavouriteTraineePositions(),
                pageable,
                userEntity.getFavouriteTraineePositions().size());
        log.info("Страница избранных стажировок пользователя получена");
        return result;
    }

    @Override
    @Transactional
    public UserEntity addFavouriteTraineePosition(Long traineeId) {
        log.info("Добавление стажировки с Id: {} в избранное пользователю", traineeId);
        UserEntity userEntity = currentUserService.getCurrentUser();
        TraineePositionEntity traineePositionEntity = traineePositionService.findById(traineeId);
        traineePositionEntity.addFavouriteTraineePositionToUser(userEntity);
        log.info("Стажировка с Id: {} добавлена в избранное пользователю", traineeId);
        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity removeFavouriteTraineePosition(Long traineeId) {
        log.info("Удаление стажировки с Id: {} из избранного у пользователя", traineeId);
        UserEntity userEntity = currentUserService.getCurrentUser();
        TraineePositionEntity traineePositionEntity = traineePositionService.findById(traineeId);
        traineePositionEntity.removeFavouriteTraineePositionFromUser(userEntity);
        log.info("Стажировка с Id: {} удалена из избранного у пользователя", traineeId);
        return userEntity;
    }
}
