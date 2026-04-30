package com.jobtrackr.server.dto.request;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyOtpRequest {
    @NotNull
    private String email;
    @NotNull
    private String otp;
}
