package com.fedag.internship.controller.user;


import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.user.TraineePositionResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.POSITION_URL;
import static com.fedag.internship.domain.util.UrlConstants.SEARCH_BY_COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + POSITION_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Позиция для стажировки", description = "Работа с позициями для стажировки")
public class TraineePositionController {
    private final TraineePositionService positionService;
    private final TraineePositionMapper positionMapper;

    @Operation(summary = "Получение позиции по Id")
    @ApiResponse(responseCode = "200", description = "Позиция найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineePositionResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Позиция не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<TraineePositionResponse> findById(@PathVariable Long id) {
        TraineePositionResponse result = Optional.of(id)
                .map(positionService::findById)
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
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

    @Operation(summary = "Получение страницы с позициями по критериям")
    @ApiResponse(responseCode = "200", description = "Позиции найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(SEARCH_BY_COMPANY_URL)
    public ResponseEntity<Page<TraineePositionResponse>> searchByCompany(@RequestParam String keyword,
                                                                         Pageable pageable) {
        Page<TraineePositionResponse> positions = positionService
                .searchByCompany(keyword, pageable)
                .map(positionMapper::toResponse);
        return new ResponseEntity<>(positions, OK);
    }
}
