package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.ResumeRequest;
import com.fedag.internship.domain.dto.request.ResumeRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminCommentResponse;
import com.fedag.internship.domain.dto.response.admin.AdminResumeResponse;
import com.fedag.internship.domain.mapper.ResumeMapper;
import com.fedag.internship.service.ResumeService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.*;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + RESUME_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Резюме", description = "Работа с резюме")
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeMapper resumeMapper;

    @Operation(summary = "Получение резюме по Id")
    @ApiResponse(responseCode = "200", description = "Резюме найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Резюме не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminResumeResponse> findById(@PathVariable Long id) {
        AdminResumeResponse result = Optional.of(id)
                .map(resumeService::findById)
                .map(resumeMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с резюме")
    @ApiResponse(responseCode = "200", description = "Резюме найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<AdminResumeResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminResumeResponse> result = resumeService.findAll(pageable)
                .map(resumeMapper::toAdminResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание резюме")
    @ApiResponse(responseCode = "201", description = "Резюме создано",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping(USER_URL)
    public ResponseEntity<AdminResumeResponse> createForUser(@RequestPart MultipartFile multipartFile,
                                                             @RequestPart @Valid ResumeRequest resumeRequest) throws IOException {
        resumeRequest.setResumeFile(multipartFile.getBytes());
        resumeRequest.setResumeFileType(multipartFile.getContentType());
        AdminResumeResponse result = Optional.of(resumeRequest)
                .map(resumeMapper::fromRequest)
                .map(resumeService::create)
                .map(resumeMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление резюме")
    @ApiResponse(responseCode = "200", description = "Резюме обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCommentResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Резюме не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminResumeResponse> update(@PathVariable Long id,
                                                      @RequestBody @Valid ResumeRequestUpdate resumeRequestUpdate) {
        AdminResumeResponse result = Optional.ofNullable(resumeRequestUpdate)
                .map(resumeMapper::fromRequestUpdate)
                .map(resumeEntity -> resumeService.update(id, resumeEntity))
                .map(resumeMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }
}
