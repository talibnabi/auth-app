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

import java.util.List;


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
        if (user.getUserRole().toString().equals("ADMIN")) {
            mailSenderService.sendPasswordResetTokenToAdmin(user, token);
        } else {
            mailSenderService.sendPasswordResetTokenToUser(user, token);
        }
    }

    @Override
    public void resetPassword(String resetToken, String newPassword) {
        User user = getUserByResetToken(resetToken);
        String encodedPassword = getEncodedPassword(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        deleteResetToken(resetToken);
    }


    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with this email.")
        );
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found with this username.")
        );
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    private User getUserByResetToken(String resetToken) {
        User user = passwordResetService.getUserByResetToken(resetToken);
        return user;
    }

    private String getEncodedPassword(String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return encodedPassword;
    }

    private void deleteResetToken(String resetToken) {
        passwordResetService.deleteResetToken(resetToken);
    }
}
