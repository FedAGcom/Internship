package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.service.GoogleCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fedag.internship.domain.util.UrlConstants.CALENDAR_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest controller GoogleCalendarController for Google Calendar.
 *
 * @author damir.iusupov
 * @since 2022-06-10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + CALENDAR_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
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
