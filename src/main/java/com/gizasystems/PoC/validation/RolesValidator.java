package com.gizasystems.PoC.validation;


import com.gizasystems.PoC.entities.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class RolesValidator implements ConstraintValidator<ValidRoles, Set<String>> {

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) {
            return true;
        }

        for (String role : roles) {
            try {
                Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }
}
