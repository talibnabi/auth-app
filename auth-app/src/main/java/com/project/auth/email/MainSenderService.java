package com.project.auth.email;

import com.project.auth.model.entity.User;

public interface MainSenderService {
    void sendToUser(User user, String adminUsername);

    void sendEmail(String toAddress, String content, String subject);
}
