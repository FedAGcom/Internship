package com.fedag.internship.service;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavouriteTraineePositionService {
    Page<TraineePositionEntity> getFavouriteTraineePositions(Long userId, Pageable pageable);

    UserEntity addFavouriteTraineePosition(Long userId, Long traineeId);

    void removeFavouriteTraineePosition(Long userId, Long traineeId);
}
