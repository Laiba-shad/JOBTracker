package com.jobtrackr.server.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service

public class JwtUtils {
        @Value("${jwt.secret}") private String secret;
        @Value("${jwt.expiration}") private long expiration;

        public String generateToken(UserDetails userDetails) {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .compact();
        }

        public String extractUsername(String token) {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }

    public Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
}
