package com.project.auth.service.impl;

import com.project.auth.email.MailSenderService;
import com.project.auth.error.exception.UserNotFoundException;
import com.project.auth.error.exception.UserNotVerifiedException;
import com.project.auth.model.dto.request.LoginRequest;
import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.model.dto.response.TokenResponse;
import com.project.auth.model.entity.User;
import com.project.auth.model.enums.UserRole;
import com.project.auth.repository.UserRepository;
import com.project.auth.security.service.JwtService;
import com.project.auth.service.AuthService;
import com.project.auth.service.UserCheckService;
import com.project.auth.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final MailSenderService mailSenderService;

    private final UserCheckService userCheckService;

    private final VerificationService verificationService;


    /**
     * Registers a new user with the provided registration request data.
     * Performs password matching checking, username duplicating checking, and email duplicating checking.
     * If checks pass, the user is created, saved, and a JWT token is generated for authentication.
     *
     * @param registrationRequest The registration request containing user information.
     */
    @Override
    public void registration(RegistrationRequest registrationRequest) {
        userCheckService.usernameDuplicatingChecking(registrationRequest);
        userCheckService.emailDuplicatingChecking(registrationRequest);
        userCheckService.passwordMatchingChecking(registrationRequest);
        User admin = getBuildedUser(registrationRequest);
        String message = mailSenderService.systemToAdminMessage(admin);
        admin.setMailSendingMessage(message);
        userRepository.save(admin);
        mailSenderService.sendToAdmin(admin);
        verificationService.verificationCodeSending(admin);
    }


    /**
     * Authenticates a user using the provided authentication request.
     * Performs user authentication and generates a JWT token for the authenticated user.
     *
     * @param loginRequest The authentication request containing user credentials.
     * @return A Token response containing a JWT token upon successful authentication.
     */
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authentication(loginRequest);
        String email = loginRequest.getEmail();
        User user = getUserByEmail(email);
        if (user.getIsVerify()) {
            String jwt = jwtService.generateToken(user);
            TokenResponse token = getToken(jwt);
            return token;
        }
        throw new UserNotVerifiedException("User is not verified. Please verify your account.");
    }


    private User getBuildedUser(RegistrationRequest registrationRequest) {
        String password = registrationRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(encodedPassword)
                .userRole(UserRole.fromId(1))
                .build();
    }

    private TokenResponse getToken(String jwt) {
        return TokenResponse.builder()
                .token(jwt)
                .build();
    }

    private void authentication(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with this email."));
    }


}
