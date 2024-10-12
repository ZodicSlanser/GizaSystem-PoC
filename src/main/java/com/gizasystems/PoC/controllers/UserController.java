package com.gizasystems.PoC.controllers;

import com.gizasystems.PoC.dtos.UserRegistrationDTO;
import com.gizasystems.PoC.entities.User;
import com.gizasystems.PoC.logging.LoggingService;
import com.gizasystems.PoC.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoggingService loggingService;


    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try{
            loggingService.logRequest("User registration attempt with username: " + registrationDTO.getUsername());

            User newUser = userService.registerNewUser(registrationDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        catch (DuplicateKeyException e) {
            loggingService.logAbnormalBehavior("User registration attempt with existing username: " + registrationDTO.getUsername());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
        catch (RuntimeException e) {
            loggingService.logAbnormalBehavior("User registration attempt with invalid data");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
