package com.project.auth.service.impl;

import com.project.auth.email.MailSenderService;
import com.project.auth.error.exception.UserNotFoundException;
import com.project.auth.error.exception.VerificationNotFoundException;
import com.project.auth.model.entity.User;
import com.project.auth.model.entity.Verification;
import com.project.auth.repository.UserRepository;
import com.project.auth.repository.VerificationRepository;
import com.project.auth.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final UserRepository userRepository;

    private final MailSenderService mailSenderService;

    private final VerificationRepository verificationRepository;

    @Override
    public Boolean verifyUser(String email, String verificationCode) {
        User user = getUserByEmail(email);
        Verification verification = getVerificationByVerificationCode(verificationCode);
        if (user != null && verification != null && !user.getIsVerify().equals(true)
                && user.getId().equals(verification.getUser().getId())) {
            user.setIsVerify(true);
            userRepository.save(user);
            verificationRepository.delete(verification);
            return true;
        }
        return false;
    }

    @Override
    public void verificationCodeSending(User user) {
        String generatedVerificationCode = generateVerificationCode();
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setVerificationCode(generatedVerificationCode);
        verificationRepository.save(verification);
        if (user.getUserRole().toString().equals("ADMIN")) {
            mailSenderService.sendVerificationCodeToAdmin(user, generatedVerificationCode);
        } else {
            mailSenderService.sendVerificationCodeToUser(user, generatedVerificationCode);
        }
    }

    @Override
    public String generateVerificationCode() {
        int codeLength = 6;
        String characters = "0123456789";
        Random random = new Random();
        String verificationCode = IntStream.range(0, codeLength)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
        return verificationCode;
    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with this email.")
        );
        return user;
    }

    private Verification getVerificationByVerificationCode(String verificationCode) {
        Verification verification = verificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(
                        () -> new VerificationNotFoundException("Verification not found with this verification code.")
                );
        return verification;
    }
}
