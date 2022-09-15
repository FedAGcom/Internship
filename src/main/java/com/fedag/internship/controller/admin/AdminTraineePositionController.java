package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.TraineePositionRequest;
import com.fedag.internship.domain.dto.request.TraineePositionRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminTraineePositionResponse;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.service.TraineePositionService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.ACTIVE;
import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.DELETED;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.POSITION_URL;
import static com.fedag.internship.domain.util.UrlConstants.SEARCH_BY_COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ADMIN + POSITION_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка Позиции для стажировки", description = "Админка для работы с позициями для стажировки")
public class AdminTraineePositionController {
    private final TraineePositionService positionService;
    private final TraineePositionMapper positionMapper;

    @Operation(summary = "Получение позиции по Id")
    @ApiResponse(responseCode = "200", description = "Позиция найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminTraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminTraineePositionResponse> findById(@PathVariable Long id) {
        AdminTraineePositionResponse companyResponse = Optional.of(id)
                .map(positionService::findById)
                .map(positionMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @Operation(summary = "Получение страницы с позициями")
    @ApiResponse(responseCode = "200", description = "Позиции найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<AdminTraineePositionResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminTraineePositionResponse> positions = positionService.findAll(pageable)
                .map(positionMapper::toAdminResponse);
        return new ResponseEntity<>(positions, OK);
    }

    @Operation(summary = "Получение страницы с позициями со статусом ACTIVE")
    @ApiResponse(responseCode = "200", description = "Позиции найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ACTIVE)
    public ResponseEntity<Page<AdminTraineePositionResponse>> findAllByActiveTrue(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<AdminTraineePositionResponse> positions = positionService.findAllByActiveTrue(pageable)
                .map(positionMapper::toAdminResponse);
        return new ResponseEntity<>(positions, OK);
    }

    @Operation(summary = "Получение страницы с позициями со статусом DELETED")
    @ApiResponse(responseCode = "200", description = "Позиции найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(DELETED)
    public ResponseEntity<Page<AdminTraineePositionResponse>> findAllByActiveFalse(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<AdminTraineePositionResponse> positions = positionService.findAllByActiveFalse(pageable)
                .map(positionMapper::toAdminResponse);
        return new ResponseEntity<>(positions, OK);
    }

    @Operation(summary = "Создание позиции")
    @ApiResponse(responseCode = "201", description = "Позиция создана",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminTraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<AdminTraineePositionResponse> create(@RequestParam Long companyId,
                                                               @RequestBody @Valid TraineePositionRequest request) {
        AdminTraineePositionResponse positionResponse = Optional.ofNullable(request)
                .map(positionMapper::fromRequest)
                .map(position -> positionService.create( companyId, position))
                .map(positionMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, CREATED);
    }

    @Operation(summary = "Обновление позиции")
    @ApiResponse(responseCode = "200", description = "Позиция обновлена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminTraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminTraineePositionResponse> update(@PathVariable Long id,
                                                               @RequestBody TraineePositionRequestUpdate update) {
        AdminTraineePositionResponse positionResponse = Optional.ofNullable(update)
                .map(positionMapper::fromRequestUpdate)
                .map(position -> positionService.update(id, position))
                .map(positionMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, OK);
    }

    @Operation(summary = "Удаление позиции")
    @ApiResponse(responseCode = "200", description = "Позиция удалена")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        positionService.deleteById(id);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Получение страницы с позициями по критериям")
    @ApiResponse(responseCode = "200", description = "Позиции найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(SEARCH_BY_COMPANY_URL)
    public ResponseEntity<Page<AdminTraineePositionResponse>> searchByPosition(@RequestParam String keyword,
                                                                              Pageable pageable) {
        Page<AdminTraineePositionResponse> positions = positionService
                .searchByPosition(keyword, pageable)
                .map(positionMapper::toAdminResponse);
        return new ResponseEntity<>(positions, OK);
    }
}
