package com.fedag.internship.controller;

import com.fedag.internship.service.UpdatingPasswordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token-auth")
@RequestMapping("/users")
public class UpdatingPasswordController {
    private final UpdatingPasswordService updatingPasswordService;

    @PostMapping("/updatePassword")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> changeUserPassword(@RequestParam("password") String password,
                                                @RequestParam("old-password") String oldPassword) {
        updatingPasswordService.changeUserPassword(password, oldPassword);
        return new ResponseEntity<>(OK);
    }
}
