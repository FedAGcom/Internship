package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequest;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminProposalCompanyResponse;
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

import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.PROPOSAL_COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.PROPOSAL_STATUS_APPROVED_URL;
import static com.fedag.internship.domain.util.UrlConstants.PROPOSAL_STATUS_REFUSED_URL;
import static com.fedag.internship.domain.util.UrlConstants.PROPOSAL_STATUS_UNDER_REVIEW_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
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
@RequestMapping(MAIN_URL + VERSION + ADMIN + PROPOSAL_COMPANY_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка Предложения о компании", description = "Админка для работы с предложением")
public class AdminProposalCompanyController {
    private final ProposalCompanyService proposalCompanyService;
    private final ProposalCompanyMapper proposalCompanyMapper;

    @Operation(summary = "Получение предложения по Id")
    @ApiResponse(responseCode = "200", description = "Предложение о компании найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminProposalCompanyResponse> findById(@PathVariable Long id) {
        AdminProposalCompanyResponse result = Optional.of(id)
                .map(proposalCompanyService::findById)
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Получение страницы с предложениями")
    @ApiResponse(responseCode = "200", description = "Предложения найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<AdminProposalCompanyResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminProposalCompanyResponse> result = proposalCompanyService.findAll(pageable)
                .map(proposalCompanyMapper::toAdminResponse);
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Создание предложения")
    @ApiResponse(responseCode = "201", description = "Предложение создано",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<AdminProposalCompanyResponse> create(@RequestBody @Valid ProposalCompanyRequest request) {
        AdminProposalCompanyResponse result = Optional.ofNullable(request)
                .map(proposalCompanyMapper::fromRequest)
                .map(proposalCompanyService::create)
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @Operation(summary = "Обновление предложения")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminProposalCompanyResponse> update(@PathVariable Long id,
                                                               @RequestBody @Valid ProposalCompanyRequestUpdate requestUpdate) {
        AdminProposalCompanyResponse result = Optional.ofNullable(requestUpdate)
                .map(proposalCompanyMapper::fromRequestUpdate)
                .map(company -> proposalCompanyService.update(id, company))
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на UNDER_REVIEW")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(PROPOSAL_STATUS_UNDER_REVIEW_URL)
    public ResponseEntity<AdminProposalCompanyResponse> setStatusUnderReview(@RequestParam Long id) {
        AdminProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusUnderReview(id))
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на REFUSED")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(PROPOSAL_STATUS_REFUSED_URL)
    public ResponseEntity<AdminProposalCompanyResponse> setStatusRefused(@RequestParam Long id) {
        AdminProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusRefused(id))
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Обновление статуса предложения на APPROVED")
    @ApiResponse(responseCode = "200", description = "Предложение обновлено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminProposalCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(PROPOSAL_STATUS_APPROVED_URL)
    public ResponseEntity<AdminProposalCompanyResponse> setStatusApproved(@RequestParam Long id) {
        AdminProposalCompanyResponse result = Optional.of(id)
                .map(company -> proposalCompanyService.setStatusApproved(id))
                .map(proposalCompanyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @Operation(summary = "Удаление предложения")
    @ApiResponse(responseCode = "200", description = "Предложение удалено")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Предложение о компании не найдено",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        proposalCompanyService.deleteById(id);
        return new ResponseEntity<>(OK);
    }
}
