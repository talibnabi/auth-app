package com.project.auth.email.impl;


import com.project.auth.email.MainSenderService;
import com.project.auth.model.entity.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import static com.project.auth.util.JavaMailSenderConstants.*;

@Component
@RequiredArgsConstructor
public class MailSenderImpl implements MainSenderService {
    private final JavaMailSender javaMailSender;

    @SneakyThrows
    @Override
    public void sendToUser(User user, String adminUsername) {
        String replacedContent = SEND_TO_USER_CONTENT.replace(USER_KEY, user.getUsername());
        replacedContent = replacedContent.replace(ADMIN_KEY, adminUsername);
        sendEmail(user.getEmail(), replacedContent, SEND_TO_USER_SUBJECT);
    }

    @SneakyThrows
    @Override
    public void sendEmail(String toAddress, String content, String subject) {
        MimeMessage message = message();
        mimeMessageHelper(message, toAddress, content, subject);
        javaMailSender.send(message);
    }

    private MimeMessage message() {
        return javaMailSender.createMimeMessage();
    }

    @SneakyThrows
    private void mimeMessageHelper(MimeMessage message, String toAddress, String content, String subject) {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(SENDER_ADDRESS, SENDER_NAME);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
    }
}
