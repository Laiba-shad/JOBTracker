// service/AuthorizationService.java
package com.jobtrackr.server.service;

import com.jobtrackr.server.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authorizationService")
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepo userRepo;

    public boolean isVerified(Authentication authentication) {
        String email = authentication.getName(); // from JWT
        return userRepo.findByEmail(email)
                .map(user -> user.isVerified())
                .orElse(false);
    }
}