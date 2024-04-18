package com.GoldenGate.GoldenGate.auth;

import com.GoldenGate.GoldenGate.user.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;

    private String email;

    private String password;

    private Role role;
}
