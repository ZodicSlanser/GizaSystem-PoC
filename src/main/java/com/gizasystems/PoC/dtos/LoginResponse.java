package com.gizasystems.PoC.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String token;
    private String username;
    private String role;
    private String errorMessage;
}
