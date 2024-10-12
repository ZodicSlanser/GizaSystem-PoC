package com.gizasystems.PoC.dtos;

import com.gizasystems.PoC.validation.ValidRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String password;

    @ValidRoles
    private Set<String> roles;
}
