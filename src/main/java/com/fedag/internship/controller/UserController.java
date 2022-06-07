package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.UserRequest;
import com.fedag.internship.domain.dto.UserRequestUpdate;
import com.fedag.internship.domain.dto.UserResponse;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
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
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = Optional.of(id)
                .map(userService::getUserById)
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, OK);
    }

    @Operation(summary = "Получение страницы с пользователями")
    @ApiResponse(responseCode = "200", description = "Пользователи найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable)
                .map(userMapper::toResponse);
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Создание пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = Optional.ofNullable(userRequest)
                .map(userMapper::fromRequest)
                .map(userService::createUser)
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, CREATED);
    }

    @Operation(summary = "Обновление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @RequestBody @Valid UserRequestUpdate userRequestUpdate) {
        UserResponse userResponse = Optional.ofNullable(userRequestUpdate)
                .map(userMapper::fromRequestUpdate)
                .map(user -> userService.updateUser(id, user))
                .map(userMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(userResponse, OK);
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удален")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(OK);
    }
}
