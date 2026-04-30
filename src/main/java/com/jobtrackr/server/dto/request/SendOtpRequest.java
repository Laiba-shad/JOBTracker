package com.jobtrackr.server.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendOtpRequest {
//expired prev otp
    @NotBlank @Email(message = "Valid email required")
    private String email;
}
