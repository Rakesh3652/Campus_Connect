package com.campusconnect.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.config.EmailService;
import com.campusconnect.config.JwtProvider;
import com.campusconnect.domain.USER_ROLE;
import com.campusconnect.exception.BadRequestException;
import com.campusconnect.exception.ResourceNotFoundException;
import com.campusconnect.model.User;
import com.campusconnect.model.VerificationCode;
import com.campusconnect.repository.UserRepository;
import com.campusconnect.repository.VerificationCodeRepository;
import com.campusconnect.request.LoginRequest;
import com.campusconnect.response.AuthResponse;
import com.campusconnect.response.SignupRequest;
import com.campusconnect.util.OtpUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @PostMapping("/send-signup-otp")
    public ResponseEntity<String> sendSignupOtp(@RequestParam String email) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new BadRequestException("User already exist with this email");
        }

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email)
        .orElse(new VerificationCode());

        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        
        verificationCodeRepository.save(verificationCode);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok("Signup OTP sent successfully to email");
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req){
        
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("OTP not found for this email"));

        if(!verificationCode.getOtp().equals(req.getOtp())){
            throw new BadRequestException("Invalid OTP");
        }

        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            throw new BadRequestException("User already exists with this email");
        }

        String[] nameParts = req.getFullName().trim().split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode("OTP_AUTH_USER"));
        user.setRole(USER_ROLE.ROLE_STUDENT);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
        

        String token = jwtProvider.generateTokenFromEmail(savedUser.getEmail());

        AuthResponse response = new AuthResponse();
        response.setJwt(token);
        response.setMessage("Signup successful");
        response.setRole(savedUser.getRole());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/send-login-otp")
    public ResponseEntity<String> sendLoginOtp(@RequestParam String email){
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with this email"));

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email)
        .orElse(new VerificationCode());

        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);
        verificationCode.setUser(user);

        verificationCodeRepository.save(verificationCode);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok("Login OTP sent successfully to email");
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("OTP not found for this email"));

        if(!verificationCode.getOtp().equals(req.getOtp())){
            throw new BadRequestException("Invalid OTP");
        }

        User user = userRepository.findByEmail(req.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found with this email"));


        verificationCodeRepository.delete(verificationCode);

        String token = jwtProvider.generateTokenFromEmail(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setJwt(token);
        response.setMessage("Login successfull");
        response.setRole(user.getRole());

        return ResponseEntity.ok(response);

    }
    
}
