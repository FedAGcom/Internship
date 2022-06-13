package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.service.GoogleCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * Rest controller GoogleCalendarController for Google Calendar.
 *
 * @author damir.iusupov
 * @since 2022-06-10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Tag(name = "Google календарь", description = "Работа с Google календарь API")
public class GoogleCalendarController {
    private final GoogleCalendarService googleCalendarService;

    @Operation(summary = "Добавление стажировки в календарь пользователю")
    @ApiResponse(responseCode = "200", description = "Стажировка успешно добавлена в календарь")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь или позиция стажировки не найдены", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<?> addEventToCalendar(@RequestParam Long traineePositionId) {
        googleCalendarService.addEventToCalendar(traineePositionId);
        return new ResponseEntity<>(OK);
    }
}
