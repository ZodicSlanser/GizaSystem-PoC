package com.gizasystems.PoC.services;

import com.gizasystems.PoC.dtos.UserRegistrationDTO;
import com.gizasystems.PoC.entities.Role;
import com.gizasystems.PoC.entities.User;
import com.gizasystems.PoC.repositories.UserRepository;
import com.gizasystems.PoC.validation.ValidRoles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserRegistrationDTO registrationDTO) {
        Optional<User> existingUser = userRepository.findByUsername(registrationDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User newUser = new User();
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        newUser.setRoles(registrationDTO.getRoles());
        newUser.setPremiumUser(registrationDTO.getRoles().contains(Role.ROLE_PREMIUM_USER));

        return userRepository.save(newUser);
    }
}
