package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequest;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.ProposalCompanyResponse;
import com.fedag.internship.domain.entity.ProposalCompanyEntity;
import com.fedag.internship.domain.mapper.ProposalCompanyMapper;
import com.fedag.internship.service.ProposalCompanyService;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * class ProposalCompanyController for class {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/proposal-company")
@Tag(name = "Предложение о компании", description = "Работа с предложением")
public class ProposalCompanyController {
    private final ProposalCompanyService proposalCompanyService;
    private final ProposalCompanyMapper proposalCompanyMapper;

    @Operation(summary = "Получение предложения по Id")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение о компании найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> findById(@PathVariable Long id) {
        ProposalCompanyResponse result = Optional.of(id)
                .map(proposalCompanyService::findById)
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с предложениями")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложения найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Page<ProposalCompanyResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<ProposalCompanyResponse> result = proposalCompanyService.findAll(pageable)
                .map(proposalCompanyMapper::toResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание предложения")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "201", description = "Предложение создано",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> create(@RequestBody @Valid ProposalCompanyRequest request) {
        ProposalCompanyResponse result = Optional.ofNullable(request)
                .map(proposalCompanyMapper::fromRequest)
                .map(proposalCompanyService::create)
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление предложения")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> update(@PathVariable Long id,
                                                          @RequestBody @Valid ProposalCompanyRequestUpdate requestUpdate) {
        ProposalCompanyResponse result = Optional.ofNullable(requestUpdate)
                .map(proposalCompanyMapper::fromRequestUpdate)
                .map(company -> proposalCompanyService.update(id, company))
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на UNDER_REVIEW")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/setStatusUnderReview")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> setStatusUnderReview(@RequestParam Long id) {
        ProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusUnderReview(id))
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на REFUSED")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/setStatusRefused")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> setStatusRefused(@RequestParam Long id) {
        ProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusRefused(id))
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на APPROVED")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/setStatusApproved")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<ProposalCompanyResponse> setStatusApproved(@RequestParam Long id) {
        ProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusApproved(id))
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Удаление предложения")
    @SecurityRequirement(name = "bearer-token-auth")
    @ApiResponse(responseCode = "200", description = "Предложение удалено")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        proposalCompanyService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}
