package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.ProposalCompanyRequest;
import com.fedag.internship.domain.dto.response.user.ProposalCompanyResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.PROPOSAL_COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * class ProposalCompanyController for class {@link ProposalCompanyEntity}.
 *
 * @author damir.iusupov
 * @since 2022-06-07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + PROPOSAL_COMPANY_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Предложение о компании", description = "Работа с предложением")
public class ProposalCompanyController {
    private final ProposalCompanyService proposalCompanyService;
    private final ProposalCompanyMapper proposalCompanyMapper;

    @Operation(summary = "Создание предложения")
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
    public ResponseEntity<ProposalCompanyResponse> create(@RequestBody @Valid ProposalCompanyRequest request) {
        ProposalCompanyResponse result = Optional.ofNullable(request)
                .map(proposalCompanyMapper::fromRequest)
                .map(proposalCompanyService::create)
                .map(proposalCompanyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }
}
