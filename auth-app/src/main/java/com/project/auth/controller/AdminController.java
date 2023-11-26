package com.project.auth.controller;


import com.project.auth.model.dto.request.RegistrationRequest;
import com.project.auth.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Create a new user.
     *
     * @param registrationRequest The user registration request data.
     * @return ResponseEntity with a success message upon user creation.
     */
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        adminService.createUser(registrationRequest);
        return ResponseEntity.ok("User created successfully!");
    }

}
