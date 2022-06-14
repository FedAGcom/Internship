package com.fedag.internship.register;

import com.fedag.internship.domain.dto.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private RegistrationService registrationService;
    @PostMapping
    public ResponseEntity<?> register(@RequestParam UserRequest request) {
        registrationService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
