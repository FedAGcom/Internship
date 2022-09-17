package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.user.UserResponse;
import com.fedag.internship.service.UserService;
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
import org.springframework.web.bind.annotation.*;


import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static com.fedag.internship.domain.util.UrlConstants.DELETE_USER_URL;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + DELETE_USER_URL)
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Действия с аккаунтом", description = "Удаление/восстановление")
public class DeleteUserController {
    private final UserService userService;

    @Operation(summary = "Удаление аккаунта")
    @ApiResponse(responseCode = "200", description = "Аккаунт удален",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping()
    public ResponseEntity<?> deleteAccount() {
        userService.deleteById();
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Восстановление аккаунта")
    @ApiResponse(responseCode = "200", description = "Аккаунт восстановлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PreAuthorize("hasAuthority('deleted')")
    @PatchMapping()
    public ResponseEntity<?> recoveryAccount() {
        userService.recoveryById();
        return new ResponseEntity<>(OK);
    }
}
