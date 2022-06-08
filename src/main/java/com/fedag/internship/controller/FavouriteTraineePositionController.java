package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.TraineePositionResponse;
import com.fedag.internship.domain.dto.response.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.FavouriteTraineePositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/favourite-trainee-positions")
@Tag(name = "Избранные стажировки", description = "Работа с избранными стажировками пользователя")
public class FavouriteTraineePositionController {
    private final FavouriteTraineePositionService favouriteTraineePositionService;
    private final TraineePositionMapper traineePositionMapper;
    private final UserMapper userMapper;

    @Operation(summary = "Получение страницы с избранными стажировками")
    @ApiResponse(responseCode = "200", description = "Избранные стажировки найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<TraineePositionResponse>> getAllFavouriteTraineePositions(
            Long userId,
            @PageableDefault(size = 5) Pageable pageable) {
        Page<TraineePositionResponse> page = favouriteTraineePositionService
                .getAllFavouriteTraineePositions(userId, pageable)
                .map(traineePositionMapper::toResponse);
        return new ResponseEntity<>(page, OK);
    }

    @Operation(summary = "Добавление стажировки в избранное к пользователю")
    @ApiResponse(responseCode = "200", description = "Стажировка добавлена в избранное",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<UserResponse> addFavouriteTraineePosition(@RequestParam Long userId,
                                                                    @RequestParam Long traineeId) {
        UserEntity userEntity = favouriteTraineePositionService.addFavouriteTraineePosition(userId, traineeId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse, OK);
    }

    @Operation(summary = "Удаление стажировки из избранного у пользователя")
    @ApiResponse(responseCode = "200", description = "Стажировка удалена из избранного")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Сущность пользователя или стажировки не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping
    public ResponseEntity<?> deleteFavouriteTraineePosition(@RequestParam Long userId,
                                                            @RequestParam Long traineeId) {
        favouriteTraineePositionService.deleteFavouriteTraineePosition(userId, traineeId);
        return new ResponseEntity<>(OK);
    }
}
