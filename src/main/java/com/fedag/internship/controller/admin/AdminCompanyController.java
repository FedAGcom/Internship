package com.fedag.internship.controller.admin;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.request.CompanyRequest;
import com.fedag.internship.domain.dto.request.CompanyRequestUpdate;
import com.fedag.internship.domain.dto.response.admin.AdminCompanyResponse;
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

import static com.fedag.internship.domain.util.UrlConstants.ACTIVE;
import static com.fedag.internship.domain.util.UrlConstants.ADMIN;
import static com.fedag.internship.domain.util.UrlConstants.COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.DELETED;
import static com.fedag.internship.domain.util.UrlConstants.ID;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.SEARCH_BY_NAME_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + ADMIN + COMPANY_URL)
@PreAuthorize("hasAuthority('admin')")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Админка Компаний", description = "Админка для работы с компаниями")
public class AdminCompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @Operation(summary = "Получение компании по Id")
    @ApiResponse(responseCode = "200", description = "Компания найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ID)
    public ResponseEntity<AdminCompanyResponse> findById(@PathVariable Long id) {
        AdminCompanyResponse adminCompanyResponse = Optional.of(id)
                .map(companyService::findById)
                .map(companyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminCompanyResponse, OK);
    }

    @Operation(summary = "Получение страницы с компаниями")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    public ResponseEntity<Page<AdminCompanyResponse>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        Page<AdminCompanyResponse> companies = companyService.findAll(pageable)
                .map(companyMapper::toAdminResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @Operation(summary = "Получение страницы с компаниями со статусом ACTIVE")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(ACTIVE)
    public ResponseEntity<Page<AdminCompanyResponse>> findAllByActiveTrue(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<AdminCompanyResponse> companies = companyService.findAllByActiveTrue(pageable)
                .map(companyMapper::toAdminResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @Operation(summary = "Получение страницы с компаниями со статусом DELETED")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(DELETED)
    public ResponseEntity<Page<AdminCompanyResponse>> findAllByActiveFalse(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<AdminCompanyResponse> companies = companyService.findAllByActiveFalse(pageable)
                .map(companyMapper::toAdminResponse);
        return new ResponseEntity<>(companies, OK);
    }

    @Operation(summary = "Создание компании")
    @ApiResponse(responseCode = "201", description = "Компания создана",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    public ResponseEntity<AdminCompanyResponse> create(@RequestParam Long userId,
                                                       @RequestBody @Valid CompanyRequest companyRequest) {
        AdminCompanyResponse adminCompanyResponse = Optional.ofNullable(companyRequest)
                .map(companyMapper::fromRequest)
                .map(companyEntity -> companyService.create(userId, companyEntity))
                .map(companyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminCompanyResponse, CREATED);
    }

    @Operation(summary = "Обновление компании")
    @ApiResponse(responseCode = "200", description = "Компания обновлена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdminCompanyResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PatchMapping(ID)
    public ResponseEntity<AdminCompanyResponse> update(@PathVariable Long id,
                                                       @RequestBody CompanyRequestUpdate companyRequestUpdate) {
        AdminCompanyResponse adminCompanyResponse = Optional.ofNullable(companyRequestUpdate)
                .map(companyMapper::fromRequestUpdate)
                .map(company -> companyService.update(id, company))
                .map(companyMapper::toAdminResponse)
                .orElseThrow();
        return new ResponseEntity<>(adminCompanyResponse, OK);
    }

    @Operation(summary = "Удаление компании")
    @ApiResponse(responseCode = "200", description = "Компания удалена")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Компания не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        companyService.deleteById(id);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Получение страницы с компаниями по критериям")
    @ApiResponse(responseCode = "200", description = "Компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping(SEARCH_BY_NAME_URL)
    public ResponseEntity<Page<AdminCompanyResponse>> searchByName(@RequestParam String keyword, Pageable pageable) {
        Page<AdminCompanyResponse> companies = companyService
                .searchByName(keyword, pageable)
                .map(companyMapper::toAdminResponse);
        return new ResponseEntity<>(companies, OK);
    }
}
