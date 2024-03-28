package com.GoldenGate.GoldenGate.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;

    private String email;

    private String password;
}
