package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.TraineePositionResponse;
import com.fedag.internship.domain.dto.response.CompanyResponse;
import com.fedag.internship.domain.dto.response.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.FavouriteTraineePositionService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/favourite-trainee-positions")
public class FavouriteTraineePositionController {
    private final UserService userService;
    private final FavouriteTraineePositionService favouriteTraineePositionService;
    private final UserMapper userMapper;
    private final TraineePositionMapper traineePositionMapper;

    @GetMapping
    public ResponseEntity<Page<TraineePositionResponse>> getAllFavouriteTraineePositions(Long userId, Pageable pageable) {
        Page<TraineePositionResponse> page = favouriteTraineePositionService.getAllFavouriteTraineePositions(userId, pageable)
                .map(traineePositionMapper::toResponse);
        return new ResponseEntity<>(page, OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addFavouriteTraineePosition(@RequestParam Long userId,
                                                                    @RequestParam Long traineeId) {
        UserEntity userEntity = favouriteTraineePositionService.addFavouriteTraineePosition(userId, traineeId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse, OK);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteFavouriteTraineePosition(@RequestParam Long userId,
                                                            @RequestParam Long traineeId) {
        favouriteTraineePositionService.deleteFavouriteTraineePosition(userId, traineeId);
        return new ResponseEntity<>(OK);
    }
}
