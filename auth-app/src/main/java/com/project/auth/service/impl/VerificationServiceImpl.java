package com.project.auth.service.impl;

import com.project.auth.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

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
}
