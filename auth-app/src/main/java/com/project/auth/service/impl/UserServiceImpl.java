package com.project.auth.service.impl;

import com.project.auth.email.MailSenderService;
import com.project.auth.error.exception.UserNotFoundException;
import com.project.auth.model.entity.User;
import com.project.auth.repository.UserRepository;
import com.project.auth.service.PasswordResetService;
import com.project.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;

    private final UserRepository userRepository;

    private final PasswordResetService passwordResetService;


    @Override
    public void passwordResetNotification(String email) {
        User user = getUserByEmail(email);
        String token = passwordResetService.generateResetToken(user);

    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with this email.")
        );
        return user;
    }
}
