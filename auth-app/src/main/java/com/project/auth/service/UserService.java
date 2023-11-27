package com.project.auth.service;

import com.project.auth.model.entity.User;

import java.util.List;

public interface UserService {

    void passwordResetNotification(String email);

    void resetPassword(String resetToken, String newPassword);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    List<User> getUserList();
}
