package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.user.CompanyResponse;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.service.CompanyService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.fedag.internship.domain.util.UrlConstants.COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.SEARCH_BY_NAME_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + COMPANY_URL)
@PreAuthorize("hasAuthority('user')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Компания", description = "Работа с компаниями")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @Operation(summary = "Получение компании по Id")
    @ApiResponse(responseCode = "200", description = "Компания найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<CompanyResponse> findById(@PathVariable Long id) {
        CompanyResponse companyResponse = Optional.of(id)
                .map(companyService::findById)
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @Operation(summary = "Получение страницы с компаниями со статусом ACTIVE")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> findAllByActiveTrue(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> companies = companyService.findAllByActiveTrue(pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @Operation(summary = "Получение страницы с компаниями по критериям")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(SEARCH_BY_NAME_URL)
    public ResponseEntity<Page<CompanyResponse>> searchByName(@RequestParam String keyword,
                                                              Pageable pageable) {
        Page<CompanyResponse> companies = companyService
                .searchByName(keyword, pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, OK);
    }
}
