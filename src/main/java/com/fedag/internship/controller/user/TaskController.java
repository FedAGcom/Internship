package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.TaskRequest;
import com.fedag.internship.domain.dto.request.TaskRequestUpdate;
import com.fedag.internship.domain.dto.response.user.TaskResponse;
import com.fedag.internship.domain.mapper.TaskMapper;
import com.fedag.internship.service.TaskService;
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

import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.TASK_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + TASK_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Задание", description = "Работа с заданиями")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Operation(summary = "Получение задания по Id")
    @ApiResponse(responseCode = "200", description = "Задание найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Задание не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<TaskResponse> findById(@PathVariable String id) {
        TaskResponse result = Optional.of(id)
                .map(taskService::findById)
                .map(taskMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с заданиями")
    @ApiResponse(responseCode = "200", description = "Задания найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<TaskResponse> result = taskService.findAll(pageable)
                .map(taskMapper::toResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание задания")
    @ApiResponse(responseCode = "201", description = "Задание создано",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest taskRequest) {
        TaskResponse result = Optional.ofNullable(taskRequest)
                .map(taskMapper::fromRequest)
                .map(taskService::create)
                .map(taskMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление задания")
    @ApiResponse(responseCode = "200", description = "Задание обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TaskResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Задание не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<TaskResponse> update(@PathVariable String id,
                                               @RequestBody @Valid TaskRequestUpdate taskRequestUpdate) {
        TaskResponse result = Optional.ofNullable(taskRequestUpdate)
                .map(taskMapper::fromRequestUpdate)
                .map(task -> taskService.update(id, task))
                .map(taskMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Удаление задания")
    @ApiResponse(responseCode = "200", description = "Задание удалено")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Задание не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        taskService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}