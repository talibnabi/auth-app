package com.project.auth.email;

import com.project.auth.model.entity.User;

public interface MailSenderService {
    void sendToUser(User user, String adminUsername);

    String adminToUserMessage(User user, String adminUsername);

    void sendToAdmin(User admin);

    String systemToAdminMessage(User admin);

    void sendEmail(String toAddress, String content, String subject);
}
