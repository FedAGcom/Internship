package com.fedag.internship.controller;

import com.fedag.internship.domain.entity.UserEntity;
import com.fedag.internship.domain.exception.InvalidOldPasswordException;
import com.fedag.internship.security.service.UserDetailServiceImpl;
import com.fedag.internship.service.UpdatingPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UpdatingPasswordController {
    private final UpdatingPasswordService updatingPasswordService;
    private final UserDetailServiceImpl userDetailService;

    @PostMapping("/updatePassword")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<?> changeUserPassword(@RequestParam("password") String password,
                                               @RequestParam("old-password") String oldPassword) {
        UserEntity user = userDetailService.getUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!updatingPasswordService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException("Invalid password");
        }
        updatingPasswordService.changeUserPassword(user, password);
        return new ResponseEntity<>(OK);
    }
}
