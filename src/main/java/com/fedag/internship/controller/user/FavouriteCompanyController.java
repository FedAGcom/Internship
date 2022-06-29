package com.fedag.internship.controller.user;

import com.fedag.internship.domain.dto.DtoErrorInfo;
import com.fedag.internship.domain.dto.response.user.CompanyResponse;
import com.fedag.internship.domain.dto.response.user.UserResponse;
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

import static com.fedag.internship.domain.util.UrlConstants.FAVOURITE_COMPANY_URL;
import static com.fedag.internship.domain.util.UrlConstants.MAIN_URL;
import static com.fedag.internship.domain.util.UrlConstants.USER_URL;
import static com.fedag.internship.domain.util.UrlConstants.VERSION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAIN_URL + VERSION + USER_URL + FAVOURITE_COMPANY_URL)
@PreAuthorize("hasAuthority('user')")
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
    public ResponseEntity<Page<CompanyResponse>> getFavouriteCompanies(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<CompanyResponse> page = favouriteCompanyService
                .getFavouriteCompanies(pageable)
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
    public ResponseEntity<UserResponse> addFavouriteCompany(@RequestParam Long companyId) {
        UserEntity userEntity = favouriteCompanyService.addFavouriteCompany(companyId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse, OK);
    }

    @Operation(summary = "Удаление компании из избранного у пользователя")
    @ApiResponse(responseCode = "200", description = "Компания удалена из избранного",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))})
    @ApiResponse(responseCode = "400", description = "Внутренняя ошибка сервера",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @ApiResponse(responseCode = "404", description = "Сущность пользователя или компании не найдена",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DtoErrorInfo.class))})
    @DeleteMapping
    public ResponseEntity<UserResponse> removeFavouriteCompany(@RequestParam Long companyId) {
        UserEntity userEntity = favouriteCompanyService.removeFavouriteCompany(companyId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse, OK);
    }
}
