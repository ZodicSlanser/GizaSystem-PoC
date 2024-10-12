package com.gizasystems.PoC.controllers;

import com.gizasystems.PoC.dtos.UserRegistrationDTO;
import com.gizasystems.PoC.entities.User;
import com.gizasystems.PoC.services.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try{
            User newUser = userService.registerNewUser(registrationDTO);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        catch (DuplicateKeyException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
