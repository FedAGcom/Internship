package com.fedag.internship.controller;


import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.TraineePositionRequest;
import com.fedag.internship.domain.dto.request.TraineePositionRequestUpdate;
import com.fedag.internship.domain.dto.response.TraineePositionResponse;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
@Tag(name = "Позиция для стажировки", description = "Работа с позициями для стажировки")
public class TraineePositionController {
    private final TraineePositionService positionService;
    private final TraineePositionMapper positionMapper;

    @Operation(summary = "Получение позиции по Id")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Позиция найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<TraineePositionResponse> findById(@PathVariable Long id) {
        TraineePositionResponse companyResponse = Optional.of(id)
                .map(positionService::findById)
                .map(positionMapper::toResponse)
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
    public ResponseEntity<Page<TraineePositionResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<TraineePositionResponse> positions = positionService.findAll(pageable)
                .map(positionMapper::toResponse);
        return new ResponseEntity<>(positions, OK);
    }

    @Operation(summary = "Создание позиции")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "201", description = "Позиция создана",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TraineePositionResponse> create(@RequestBody @Valid TraineePositionRequest request) {
        TraineePositionResponse positionResponse = Optional.ofNullable(request)
                .map(positionMapper::fromRequest)
                .map(positionService::create)
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, CREATED);
    }

    @Operation(summary = "Обновление позиции")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Позиция обновлена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<TraineePositionResponse> update(@PathVariable Long id,
                                                          @RequestBody TraineePositionRequestUpdate update) {
        TraineePositionResponse positionResponse = Optional.ofNullable(update)
                .map(positionMapper::fromRequestUpdate)
                .map(position -> positionService.update(id, position))
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, OK);
    }

    @Operation(summary = "Удаление позиции")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Позиция удалена")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
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
    @GetMapping("/searchposition")
    public ResponseEntity<Page<TraineePositionResponse>> searchByCompany(@RequestParam String keyword,
                                                                         Pageable pageable) {
        Page<TraineePositionResponse> positions = positionService
                .searchByCompany(keyword, pageable)
                .map(positionMapper::toResponse);
        return new ResponseEntity<>(positions, OK);
    }
}
