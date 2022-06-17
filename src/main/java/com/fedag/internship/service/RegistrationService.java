package com.fedag.internship.service;

import com.fedag.internship.domain.dto.request.RegistrationRequest;

public interface RegistrationService {
    void register(RegistrationRequest request);

    void confirm(String token);
}

