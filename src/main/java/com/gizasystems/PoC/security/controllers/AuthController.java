package com.gizasystems.PoC.security.controllers;

import com.gizasystems.PoC.dtos.LoginResponse;
import com.gizasystems.PoC.entities.User;
import com.gizasystems.PoC.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User loginUser) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
            System.out.println("loginUser: " + loginUser);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
            System.out.println("userDetails: " + userDetails);
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());


            return ResponseEntity.ok(new LoginResponse(jwtToken, userDetails.getUsername(), userDetails.getAuthorities().toString(), null));
        } catch (AuthenticationException e) {
            return new ResponseEntity<LoginResponse>(new LoginResponse(null, null, null, "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
    }
}
