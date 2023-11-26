package com.project.auth.controller;

import com.project.auth.model.dto.request.LoginRequest;
import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.model.dto.response.TokenResponse;
import com.project.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        authService.registration(registrationRequest);
        return ResponseEntity.ok("Registration successfully!");
    }


    /**
     * Authenticate and log in a user.
     *
     * @param loginRequest The authentication request data.
     * @return ResponseEntity with Token response
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        authService.login(loginRequest);
        return ResponseEntity.ok(authService.login(loginRequest));
    }


}
