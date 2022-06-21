package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.CompanyRequest;
import com.fedag.internship.domain.dto.request.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.CompanyResponse;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.service.CompanyElasticSearchService;
import com.fedag.internship.service.CompanyService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "Компания", description = "Работа с компаниями")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final CompanyElasticSearchService companyElasticSearchService;

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
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        CompanyResponse companyResponse = Optional.of(id)
                .map(companyService::getCompanyById)
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @Operation(summary = "Получение страницы с компаниями")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllCompanies(@PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> companies = companyService.getAllCompanies(pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @Operation(summary = "Создание компании")
    @ApiResponse(responseCode = "201", description = "Компания создана",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CompanyResponse> createCompany(@RequestParam Long userId,
                                                         @RequestBody @Valid CompanyRequest companyRequest) {
        CompanyResponse companyResponse = Optional.ofNullable(companyRequest)
                .map(companyMapper::fromRequest)
                .map(companyEntity -> companyService.createCompany(userId, companyEntity))
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, CREATED);
    }

    @Operation(summary = "Обновление компании")
    @ApiResponse(responseCode = "200", description = "Компания обновлена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id,
                                                         @RequestBody CompanyRequestUpdate companyRequestUpdate) {
        CompanyResponse companyResponse = Optional.ofNullable(companyRequestUpdate)
                .map(companyMapper::fromRequestUpdate)
                .map(company -> companyService.updateCompany(id, company))
                .map(companyMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @Operation(summary = "Удаление компании")
    @ApiResponse(responseCode = "200", description = "Компания удалена")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Получение страницы с компаниями по критериям")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping("/search")
    public ResponseEntity<Page<CompanyResponse>> search(@RequestParam String keyword, Pageable pageable) {
        Page<CompanyResponse> companies = companyService.searchCompanyByName(keyword, pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(companies, OK);
    }
}
