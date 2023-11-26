package com.project.auth.service;

import com.project.auth.model.dto.request.RegistrationRequest;

public interface UserCheckService {
    void passwordMatchingChecking(RegistrationRequest registerRequest);

    void usernameDuplicatingChecking(RegistrationRequest registerRequest);

    void emailDuplicatingChecking(RegistrationRequest registerRequest);
}
