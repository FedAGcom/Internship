package com.fedag.internship.controller;

import com.fedag.internship.service.UpdatingPasswordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-token-auth")
@RequestMapping("/change-password")
public class UpdatingPasswordController {
    private final UpdatingPasswordService updatingPasswordService;

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> changeUserPassword(@RequestParam("password") String password,
                                                @RequestParam("old-password") String oldPassword) {
        updatingPasswordService.changeUserPassword(password, oldPassword);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam String token) {
        updatingPasswordService.confirm(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
