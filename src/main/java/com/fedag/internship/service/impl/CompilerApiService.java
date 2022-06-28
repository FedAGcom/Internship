package com.fedag.internship.service.impl;

import com.fedag.internship.domain.dto.request.CompilerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CompilerApiService {
    public static ResponseEntity<String> execute(CompilerRequest compilerRequest) {
        return new RestTemplate().postForEntity("${jdoodle.compiler-url}", setCredentials(compilerRequest), String.class);
    }

    private static CompilerRequest setCredentials(CompilerRequest compilerRequest) {
        compilerRequest.setClientId("${jdoodle.client-id}");
        compilerRequest.setClientSecret("${jdoodle.client-secret}");
        return compilerRequest;
    }
}
