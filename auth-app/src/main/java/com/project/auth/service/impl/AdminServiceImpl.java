package com.project.auth.service.impl;

import com.project.auth.email.MailSenderService;
import com.project.auth.mapper.UserMapper;
import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.model.entity.User;
import com.project.auth.model.enums.UserRole;
import com.project.auth.repository.UserRepository;
import com.project.auth.service.AdminService;
import com.project.auth.service.UserCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;

    private final UserCheckService userCheckService;


    /**
     * Creates a new user based on the provided registration request.
     * Performs password matching checking, username duplicating checking, and email duplicating checking.
     * The user is created, assigned the USER role, and associated with the same organization as the admin.
     *
     * @param registrationRequest The registration request containing user information.
     */
    @Override
    public void createUser(RegistrationRequest registrationRequest) {
        userCheckService.usernameDuplicatingChecking(registrationRequest);
        userCheckService.passwordMatchingChecking(registrationRequest);
        userCheckService.emailDuplicatingChecking(registrationRequest);
        String adminUsername = getAdminUsernameFromSecurityContextHolder();
        User user = userMapper.toUser(registrationRequest);
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        user.setPassword(encodedPassword);
        User lastUser = getBuildedUser(user, adminUsername, mailSenderService);
        userRepository.save(lastUser);
        mailSenderService.sendToUser(lastUser, adminUsername);
    }

    private User getBuildedUser(User user, String usernameAdmin, MailSenderService mailSenderService) {
        UserRole userRole = UserRole.fromId(2);
        String message = mailSenderService.adminToUserMessage(user, usernameAdmin);
        user.setUserRole(userRole);
        user.setMailSendingMessage(message);
        return user;
    }

    private String getAdminUsernameFromSecurityContextHolder() {
        return ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

}
