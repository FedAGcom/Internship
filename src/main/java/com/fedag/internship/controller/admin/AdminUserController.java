package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.UserRequest;
import com.fedag.internship.domain.dto.request.UserRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminUserResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.DELETED;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.USER;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static com.fedag.internship.domain.util.UrlConstants.BLOCKED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * class AdminController
 *
 * @author damir.iusupov
 * @since 2022-06-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ADMIN + USER_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка пользователей", description = "Админка для работы с пользователями")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Получение пользователя по Id")
    @ApiResponse(responseCode = "200", description = "Пользователь найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminUserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminUserResponse> findById(@PathVariable Long id) {
        AdminUserResponse adminUserResponse = Optional.of(id)
                .map(userService::findById)
                .map(userMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminUserResponse, OK);
    }

    @Operation(summary = "Получение страницы с пользователями с ролью USER")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(USER)
    public ResponseEntity<Page<AdminUserResponse>> findAllByRoleUser(@PageableDefault(size = 5)
                                                                     Pageable pageable) {
        Page<AdminUserResponse> users = userService.findAllByRoleUser(pageable)
                .map(userMapper::toAdminResponse);
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Получение страницы с пользователями с ролью BLOCKED")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(BLOCKED)
    public ResponseEntity<Page<AdminUserResponse>> findAllByRoleBlocked(@PageableDefault(size = 5)
                                                                        Pageable pageable) {
        Page<AdminUserResponse> users = userService.findAllByRoleBlocked(pageable)
                .map(userMapper::toAdminResponse);
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Получение страницы с пользователями с ролью DELETED")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(DELETED)
    public ResponseEntity<Page<AdminUserResponse>> findAllByRoleDeleted(@PageableDefault(size = 5)
                                                                        Pageable pageable) {
        Page<AdminUserResponse> users = userService.findAllByRoleDeleted(pageable)
                .map(userMapper::toAdminResponse);
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
    public ResponseEntity<Page<AdminUserResponse>> findAll(@PageableDefault(size = 5)
                                                           Pageable pageable) {
        Page<AdminUserResponse> users = userService.findAll(pageable)
                .map(userMapper::toAdminResponse);
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Создание пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminUserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<AdminUserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        AdminUserResponse adminUserResponse = Optional.ofNullable(userRequest)
                .map(userMapper::fromRequest)
                .map(userService::create)
                .map(userMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminUserResponse, CREATED);
    }

    @Operation(summary = "Обновление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminUserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminUserResponse> update(@PathVariable Long id,
                                                    @RequestBody @Valid UserRequestUpdate userRequestUpdate) {
        AdminUserResponse adminUserResponse = Optional.ofNullable(userRequestUpdate)
                .map(userMapper::fromRequestUpdate)
                .map(user -> userService.update(id, user))
                .map(userMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminUserResponse, OK);
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> blockById(@PathVariable Long id) {
        userService.blockById(id);
        return new ResponseEntity<>(OK);
    }
}
