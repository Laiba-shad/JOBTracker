package com.jobtrackr.server.service;

import com.jobtrackr.server.dto.request.LoginRequest;
import com.jobtrackr.server.dto.request.RegisterRequest;
import com.jobtrackr.server.dto.response.LoginResponse;
import com.jobtrackr.server.model.User;
import com.jobtrackr.server.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//service is stateless, fields aer injected
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    public LoginResponse register(RegisterRequest request){

        if(userRepo.findByEmail(request.getEmail()).equals(request.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getUsername());
        userRepo.save(user);
        UserDetails ud = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(ud);
        return LoginResponse.builder()
                .status(201).message("Registered").email(request.getEmail())
                .id(user.getUserId().toString()).token(token)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        UserDetails ud = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(ud);
        return LoginResponse.builder()
                .status(200).message("Login success").email(request.getEmail())
                .id("from-user").token(token)  // Fetch ID if needed
                .build();
    }
}
