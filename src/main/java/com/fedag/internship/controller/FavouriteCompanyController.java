package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.CompanyResponse;
import com.fedag.internship.domain.dto.response.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.FavouriteCompanyService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/favourite-companies")
@SecurityRequirement(name = "bearer-token-auth")
@Tag(name = "Избранные компании", description = "Работа с избранными компаниями пользователя")
public class FavouriteCompanyController {
    private final FavouriteCompanyService favouriteCompanyService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @Operation(summary = "Получение страницы с избранными компаниями")
    @ApiResponse(responseCode = "200", description = "Избранные компании найдены",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Page.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Page<CompanyResponse>> getAllFavouriteCompanies(Long userId,
                                                                          @PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> page = favouriteCompanyService
                .getAllFavouriteCompanies(userId, pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(page, OK);
    }

    @Operation(summary = "Добавление компании в избранное к пользователю")
    @ApiResponse(responseCode = "200", description = "Компания добавлена в избранное",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<UserResponse> addFavouriteCompany(@RequestParam Long userId,
                                                            @RequestParam Long companyId) {
        UserEntity userEntity = favouriteCompanyService.addFavouriteCompany(userId, companyId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse, OK);
    }

    @Operation(summary = "Удаление компании из избранного у пользователя")
    @ApiResponse(responseCode = "200", description = "Компания удалена из избранного")
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Сущность пользователя или компании не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> deleteFavouriteCompany(@RequestParam Long userId,
                                                    @RequestParam Long companyId) {
        favouriteCompanyService.deleteFavouriteCompany(userId, companyId);
        return new ResponseEntity<>(OK);
    }
}
