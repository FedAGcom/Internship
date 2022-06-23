package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.admin.AdminTraineePositionResponse;
import com.fedag.internship.domain.dto.response.admin.AdminUserResponse;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.FavouriteTraineePositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.FAVOURITE_POSITION_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ADMIN + USER_URL + FAVOURITE_POSITION_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка Избранных стажировок",
        description = "Админка для работы с избранными стажировками пользователя")
public class AdminFavouriteTraineePositionController {
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
    public ResponseEntity<Page<AdminTraineePositionResponse>> getFavouriteTraineePositions(
            @RequestParam Long userId,
            @PageableDefault(size = 5) Pageable pageable) {
        Page<AdminTraineePositionResponse> page = favouriteTraineePositionService
                .getFavouriteTraineePositions(userId, pageable)
                .map(traineePositionMapper::toAdminResponse);
        return new ResponseEntity<>(page, OK);
    }

    @Operation(summary = "Добавление стажировки в избранное к пользователю")
    @ApiResponse(responseCode = "200", description = "Стажировка добавлена в избранное",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminUserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<AdminUserResponse> addFavouriteTraineePosition(@RequestParam Long userId,
                                                                         @RequestParam Long traineeId) {
        UserEntity userEntity = favouriteTraineePositionService
                .addFavouriteTraineePosition(userId, traineeId);
        AdminUserResponse adminUserResponse = userMapper.toAdminResponse(userEntity);
        return new ResponseEntity<>(adminUserResponse, OK);
    }

    @Operation(summary = "Удаление стажировки из избранного у пользователя")
    @ApiResponse(responseCode = "200", description = "Стажировка удалена из избранного",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminUserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Сущность пользователя или стажировки не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping
    public ResponseEntity<AdminUserResponse> removeFavouriteTraineePosition(@RequestParam Long userId,
                                                                            @RequestParam Long traineeId) {
        UserEntity userEntity = favouriteTraineePositionService
                .removeFavouriteTraineePosition(userId, traineeId);
        AdminUserResponse adminUserResponse = userMapper.toAdminResponse(userEntity);
        return new ResponseEntity<>(adminUserResponse, OK);
    }
}
