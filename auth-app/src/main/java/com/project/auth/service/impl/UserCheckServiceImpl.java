package com.project.auth.service.impl;

import com.project.auth.error.exception.EmailDuplicateException;
import com.project.auth.error.exception.PasswordNotMatchedException;
import com.project.auth.error.exception.UsernameDuplicateException;
import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.repository.UserRepository;
import com.project.auth.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckServiceImpl implements UserCheckService {

    private final UserRepository userRepository;

    @Override
    public void passwordMatchingChecking(RegistrationRequest registerRequest) {
        if (!registerRequest.isPasswordsMatched()) {
            throw new PasswordNotMatchedException("User's password not matched.");
        }
    }

    @Override
    public void usernameDuplicatingChecking(RegistrationRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameDuplicateException("You cannot create a new user with the same username.");
        }
    }

    @Override
    public void emailDuplicatingChecking(RegistrationRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException("You cannot create a new user with the same email.");
        }
    }

}
