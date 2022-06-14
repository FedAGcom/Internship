package com.fedag.internship.register;

import com.fedag.internship.domain.dto.request.UserRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        registrationService.register(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> confirm(@RequestParam String token) {
        registrationService.confirm(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
