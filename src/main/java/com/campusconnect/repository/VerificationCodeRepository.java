package com.campusconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCodeRepository, Long> {
    // Find by email (for OTP verification)
    Optional<VerificationCodeRepository> findByEmail(String email);
    // Find by OTP
    Optional<VerificationCodeRepository> findByOtp(String otp);
    // Delete after verification (optional but useful)
    void deleteByEmail(String email);
}
