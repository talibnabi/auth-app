package com.project.auth.service;

import com.project.auth.model.entity.User;

public interface VerificationService {

    Boolean verifyUser(String email, String verificationCode);

    void verificationCodeSending(User user);

    String generateVerificationCode();
}
