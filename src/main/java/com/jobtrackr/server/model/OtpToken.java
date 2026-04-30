package com.jobtrackr.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "otp_token")
public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String hashOtp;
    private int attempts;
    private boolean used = false;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime consumedAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
