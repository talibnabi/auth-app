package com.project.auth.service;

import com.project.auth.model.dto.request.RegistrationRequest;

public interface AdminService {
    void createUser(RegistrationRequest registrationRequest);
}
