package com.project.auth.controller;


import com.project.auth.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/verification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("email") String email,
                                         @RequestParam("verificationCode") String verificationCode) {

        Boolean isVerify = verificationService.verifyUser(email, verificationCode);
        if (isVerify) {
            return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Verification failed. Invalid email or verification code.", HttpStatus.BAD_REQUEST);
    }
}
