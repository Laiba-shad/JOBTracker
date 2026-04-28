package com.jobtrackr.server.controller;

import com.jobtrackr.server.dto.request.RegisterRequest;
import com.jobtrackr.server.dto.response.LoginResponse;
import com.jobtrackr.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);  // Now returns LoginResponse + token
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}