package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.CompanyResponse;
import com.fedag.internship.domain.dto.UserResponse;
import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.mapper.CompanyMapper;
import com.fedag.internship.domain.mapper.UserMapper;
import com.fedag.internship.service.FavouriteCompanyService;
import com.fedag.internship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/favourite-companies")
public class FavouriteCompanyController {
    private final UserService userService;
    private final FavouriteCompanyService favouriteCompanyService;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getAllFavouriteCompanies(Long userId, Pageable pageable) {
        Page<CompanyResponse> page = favouriteCompanyService.getAllFavouriteCompanies(userId, pageable)
                .map(companyMapper::toResponse);
        return new ResponseEntity<>(page, OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addFavouriteCompany(@RequestParam Long userId,
                                                            @RequestParam Long companyId) {
        UserEntity userEntity = favouriteCompanyService.addFavouriteCompany(userId, companyId);
        UserResponse userResponse = userMapper.toResponse(userEntity);
        return new ResponseEntity<>(userResponse,OK);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteFavouriteCompany(@RequestParam Long userId,
                                                    @RequestParam Long companyId) {
        favouriteCompanyService.deleteFavouriteCompany(userId, companyId);
        return new ResponseEntity<>(OK);
    }
}
