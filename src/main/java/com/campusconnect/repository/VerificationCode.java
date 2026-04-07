package com.campusconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCode extends JpaRepository<VerificationCode, Long> {
    // Find by email (for OTP verification)
    Optional<VerificationCode> findByEmail(String email);
    // Find by OTP
    Optional<VerificationCode> findByOtp(String otp);
    // Delete after verification (optional but useful)
    void deleteByEmail(String email);
}
