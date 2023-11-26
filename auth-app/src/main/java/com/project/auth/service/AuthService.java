package com.project.auth.service;

import com.project.auth.model.dto.request.LoginRequest;
import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.model.dto.response.TokenResponse;

public interface AuthService {
    void registration(RegistrationRequest registrationRequest);

    TokenResponse login(LoginRequest loginRequest);
}
