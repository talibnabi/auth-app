package com.project.auth.service.impl;

import com.project.auth.error.exception.PasswordResetTokenNotFoundException;
import com.project.auth.model.entity.PasswordResetToken;
import com.project.auth.model.entity.User;
import com.project.auth.repository.PasswordResetTokenRepository;
import com.project.auth.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public String generateResetToken(User user) {
        String resetToken = generateRandomToken();
        PasswordResetToken passwordResetToken = getPasswordResetToken(user, resetToken);
        passwordResetTokenRepository.save(passwordResetToken);
        return resetToken;
    }

    @Override
    public User getUserByResetToken(String resetToken) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenByResetToken(resetToken);
        User user = passwordResetToken.getUser();
        return user;
    }

    @Override
    public void deleteResetToken(String resetToken) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenByResetToken(resetToken);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public String generateRandomToken() {
        int codeLength = 6;
        String characters = "0123456789";
        Random random = new Random();
        String randomToken = IntStream.range(0, codeLength)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
        return randomToken;
    }

    private PasswordResetToken getPasswordResetToken(User user, String resetToken) {
        return PasswordResetToken.builder()
                .user(user)
                .resetPasswordToken(resetToken)
                .build();
    }

    private PasswordResetToken getPasswordResetTokenByResetToken(String resetToken) {
        return passwordResetTokenRepository.findByResetPasswordToken(resetToken)
                .orElseThrow(
                        () -> new PasswordResetTokenNotFoundException("PasswordResetToken not found with resetToken.")
                );
    }

}
