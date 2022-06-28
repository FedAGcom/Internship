package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.CommentRequest;
import com.fedag.internship.domain.dto.request.CommentRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminCommentResponse;
import com.fedag.internship.domain.entity.CommentEntity;
import com.fedag.internship.domain.mapper.CommentMapper;
import com.fedag.internship.service.CommentService;
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

import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.COMMENT_URL;
import static com.fedag.internship.domain.util.UrlConstants.COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.POSITION_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link CommentEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ADMIN + COMMENT_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка Комментариев", description = "Админка для работы с комментариями")
public class AdminCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(summary = "Получение комментариев по Id")
    @ApiResponse(responseCode = "200", description = "Комментарий найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Комментарий не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminCommentResponse> findById(@PathVariable Long id) {
        AdminCommentResponse result = Optional.of(id)
                .map(commentService::findById)
                .map(commentMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с комментариями")
    @ApiResponse(responseCode = "200", description = "Комментарии найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<AdminCommentResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminCommentResponse> result = commentService.findAll(pageable)
                .map(commentMapper::toAdminResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание комментария для компании")
    @ApiResponse(responseCode = "201", description = "Комментарий создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(COMPANY_URL)
    public ResponseEntity<AdminCommentResponse> createForCompany(
            @RequestParam Long companyId,
            @RequestBody @Valid CommentRequest commentRequest) {
        AdminCommentResponse result = Optional.ofNullable(commentRequest)
                .map(commentMapper::fromRequest)
                .map(comment -> commentService.createForCompany(companyId, comment))
                .map(commentMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Создание комментария для позиции стажировки")
    @ApiResponse(responseCode = "201", description = "Комментарий создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(POSITION_URL)
    public ResponseEntity<AdminCommentResponse> createForTraineePosition(
            @RequestParam Long positionId,
            @RequestBody @Valid CommentRequest commentRequest) {
        AdminCommentResponse result = Optional.ofNullable(commentRequest)
                .map(commentMapper::fromRequest)
                .map(comment -> commentService.createForTraineePosition(positionId, comment))
                .map(commentMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponse(responseCode = "200", description = "Комментарий обновлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Комментарий не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminCommentResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestUpdate commentRequestUpdate) {
        AdminCommentResponse result = Optional.ofNullable(commentRequestUpdate)
                .map(commentMapper::fromRequestUpdate)
                .map(comment -> commentService.update(id, comment))
                .map(commentMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponse(responseCode = "200", description = "Комментарий удален")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Комментарий не найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}
