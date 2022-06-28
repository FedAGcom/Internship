package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.service.UpdatingPasswordService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fedag.internship.domain.util.UrlConstants.CHANGE_PWD_URL;
import static com.fedag.internship.domain.util.UrlConstants.CONFIRM_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + USER_URL + CHANGE_PWD_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Смена пароля", description = "Работа со сменой пароля у пользователя")
public class UpdatingPasswordController {
    private final UpdatingPasswordService updatingPasswordService;

    @Operation(summary = "Запрос на изменение пароля")
    @ApiResponse(responseCode = "201", description = "Запрос создан")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<?> changeUserPassword(@RequestParam("password") String password,
                                                @RequestParam("old-password") String oldPassword) {
        updatingPasswordService.changeUserPassword(password, oldPassword);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Подтверждение запроса на смену пароля")
    @ApiResponse(responseCode = "200", description = "Подтверждение успешно")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(CONFIRM_URL)
    public ResponseEntity<?> confirm(@RequestParam String token) {
        updatingPasswordService.confirm(token);
        return new ResponseEntity<>(OK);
    }
}
