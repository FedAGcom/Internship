package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.user.UserResponse;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.UserService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.USER;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + USER_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Пользователь", description = "Работа с пользователями")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Получение пользователя по Id")
    @ApiResponse(responseCode = "200", description = "Пользователь найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserResponse result = Optional.of(id)
                .map(userService::findById)
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с пользователями с ролью USER")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(USER)
    public ResponseEntity<Page<UserResponse>> findAllByRoleUser(@PageableDefault(size = 5) Pageable pageable) {
        Page<UserResponse> users = userService.findAllByRoleUser(pageable)
                .map(userMapper::toResponse);
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Получение страницы с пользователями")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<UserResponse> users = userService.findAll(pageable)
                .map(userMapper::toResponse);
        return new ResponseEntity<>(users, OK);
    }
}
