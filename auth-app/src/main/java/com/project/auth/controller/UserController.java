package com.project.auth.controller;


import com.project.auth.model.entity.User;
import com.project.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/reset-password-notification")
    public ResponseEntity<String> resetPasswordNotification(@RequestParam("email") String email) {
        userService.passwordResetNotification(email);
        return new ResponseEntity<>("Reset password token was sent to user mail.", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("resetToken") String resetToken,
                                                @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(resetToken, newPassword);
        return new ResponseEntity<>("Password was reset and changed.", HttpStatus.OK);
    }

    @GetMapping("/find-user/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find-user/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/find-all-user")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
