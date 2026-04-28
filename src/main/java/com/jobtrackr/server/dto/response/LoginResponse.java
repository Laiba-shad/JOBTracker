package com.jobtrackr.server.dto.response;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private int status;
    private String message;
    private String email;
    private String id;
    private String token;  // JWT here
}