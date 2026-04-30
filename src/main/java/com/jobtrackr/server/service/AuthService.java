package com.jobtrackr.server.service;

import com.jobtrackr.server.dto.request.LoginRequest;
import com.jobtrackr.server.dto.request.RegisterRequest;
import com.jobtrackr.server.dto.request.SendOtpRequest;
import com.jobtrackr.server.dto.request.VerifyOtpRequest;
import com.jobtrackr.server.dto.response.ApiResponse;
import com.jobtrackr.server.dto.response.LoginResponse;
import com.jobtrackr.server.exception.*;
import com.jobtrackr.server.model.OtpToken;
import com.jobtrackr.server.model.User;
import com.jobtrackr.server.repo.OtpRepo;
import com.jobtrackr.server.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
//service is stateless??, fields aer injected
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final OtpRepo OtpRepo;
    private final EmailService emailService;


    private static final int OTP_EXPIRY_LIMIT= 5;
    private static final int OTP_LENGTH = 6;
    private static final int MAX_ATTEMPTS = 3;
    private  static final SecureRandom random = new SecureRandom();
    private final RestClient.Builder builder;


    public ApiResponse<Void> register(RegisterRequest request){

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getUsername());
        user.setVerified(false);
        userRepo.save(user);

        sendOtp(request.getEmail());
        return ApiResponse.created("Account created successfully check your email for otp", null);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!user.isVerified()){
            throw new UserNotVerifiedException();
        }
        UserDetails ud = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(ud);

        return LoginResponse.builder()
                .status(200)
                .message("Login successful")
                .email(user.getEmail())
                .id(user.getUserId().toString())
                .token(token)
                .build();
    }
    private void sendOtpToEmail(String email) {
        // Invalidate any previous unused OTPs
        OtpRepo.invalidateAllByEmail(email);

        // Generate plain OTP — send to user
        String otp = generateOtp();

        // Store only the hash — never plain text
        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setHashOtp(passwordEncoder.encode(otp));
        token.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_LIMIT));
        token.setAttempts(0);
        token.setUsed(false);
        OtpRepo.save(token);

        emailService.sendOtpEmail(email, otp);
        System.out.println("========= OTP FOR TESTING: " + otp + " =========");

    }

    private String generateOtp(){
        StringBuilder otp =new StringBuilder(OTP_LENGTH);
        for (int i = 0; i <OTP_LENGTH ; i++) {
            int value = random.nextInt(10);
            otp.append(value);
        }
        return otp.toString();
    }
    public ApiResponse<Void> sendOtp(String email) {

       userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("no account exists on this email" + email));
       sendOtpToEmail(email);
       return ApiResponse.created("OTP sent successfully to your email" + email, null);
    }

    public ApiResponse<Void> verifyOtp(VerifyOtpRequest request) {

       OtpToken otpToken = OtpRepo.findTopByEmailOrderByCreatedAtDesc(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("no otp exists for this email" + request.getEmail()));

        if (otpToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException();
        }
        if (otpToken.getAttempts() >= MAX_ATTEMPTS) {
            throw new OtpInvalidException();
        }
        if (!passwordEncoder.matches(request.getOtp(), otpToken.getHashOtp())) {
            otpToken.setAttempts(otpToken.getAttempts() + 1);
            OtpRepo.save(otpToken);
            throw new OtpInvalidException();
        }
        otpToken.setUsed(true);
        otpToken.setConsumedAt(LocalDateTime.now());
        OtpRepo.save(otpToken);
        System.out.println("OTP verified successfully");
        System.out.println(otpToken);
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setVerified(true);
        userRepo.save(user);

        return ApiResponse.success("Email verified successfully", null);
    }
}
