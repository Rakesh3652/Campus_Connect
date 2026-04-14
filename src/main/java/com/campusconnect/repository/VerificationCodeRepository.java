package com.campusconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    
    Optional<VerificationCode> findByEmail(String email);
    Optional<VerificationCode> findByOtp(String otp);
    Optional<VerificationCode> findTopByEmailOrderByIdDesc(String email);
}
