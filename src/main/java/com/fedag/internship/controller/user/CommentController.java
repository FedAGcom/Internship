package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.CommentRequest;
import com.fedag.internship.domain.dto.request.CommentRequestUpdate;
import com.fedag.internship.domain.dto.response.user.CommentResponse;
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
@RequestMapping(MAIN_URL + VERSION + COMMENT_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Комментарий", description = "Работа с комментариями")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(summary = "Получение комментариев по Id")
    @ApiResponse(responseCode = "200", description = "Комментарий найден",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Комментарий не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        CommentResponse result = Optional.of(id)
                .map(commentService::findById)
                .map(commentMapper::toResponse)
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
    public ResponseEntity<Page<CommentResponse>> findAll(@PageableDefault(size = 5)
                                                         Pageable pageable) {
        Page<CommentResponse> result = commentService.findAll(pageable)
                .map(commentMapper::toResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание комментария для компании")
    @ApiResponse(responseCode = "201", description = "Комментарий создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(COMPANY_URL)
    public ResponseEntity<CommentResponse> createForCompany(
            @RequestParam Long companyId,
            @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse result = Optional.ofNullable(commentRequest)
                .map(commentMapper::fromRequest)
                .map(comment -> commentService.createForCompany(companyId, comment))
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Создание комментария для позиции стажировки")
    @ApiResponse(responseCode = "201", description = "Комментарий создан",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(POSITION_URL)
    public ResponseEntity<CommentResponse> createForTraineePosition(
            @RequestParam Long positionId,
            @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse result = Optional.ofNullable(commentRequest)
                .map(commentMapper::fromRequest)
                .map(comment -> commentService.createForTraineePosition(positionId, comment))
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление комментария")
    @ApiResponse(responseCode = "200", description = "Комментарий обновлен",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Комментарий не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestUpdate commentRequestUpdate) {
        CommentResponse result = Optional.ofNullable(commentRequestUpdate)
                .map(commentMapper::fromRequestUpdate)
                .map(comment -> commentService.update(id, comment))
                .map(commentMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }
}
