package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.request.CompilerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CompilerApiService {

    private static final String COMPILER_URL = "https://api.jdoodle.com/v1/execute";

    public static ResponseEntity<String> execute(CompilerRequest compilerRequest) {
        return new RestTemplate().postForEntity(COMPILER_URL, compilerRequest, String.class);
    }
}
