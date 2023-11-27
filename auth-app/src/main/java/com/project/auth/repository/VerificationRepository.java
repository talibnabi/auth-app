package com.project.auth.repository;

import com.project.auth.model.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Integer> {

    Optional<Verification> findByVerificationCode(String verificationCode);
}
