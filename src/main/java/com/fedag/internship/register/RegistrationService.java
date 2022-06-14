package com.fedag.internship.register;

import com.fedag.internship.domain.dto.request.UserRequest;

public interface RegistrationService {

    void register(UserRequest request);

    void confirm(String token);
}

