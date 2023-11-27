package com.project.auth.service;

import com.project.auth.model.entity.User;

public interface PasswordResetService {

    String generateResetToken(User user);

    User getUserByResetToken(String resetToken);

    void deleteResetToken(String resetToken);

    String generateRandomToken();

}
