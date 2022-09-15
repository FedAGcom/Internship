package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.request.CompilerRequest;
import com.fedag.internship.service.impl.CompilerApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compiler")
public class CompilerApiController {
    @PreAuthorize("hasAuthority('read')")
    @PostMapping("/compile")
    public ResponseEntity<String> doCompile(@RequestBody CompilerRequest compilerRequest) {
        return CompilerApiService.execute(compilerRequest);
    }
}
