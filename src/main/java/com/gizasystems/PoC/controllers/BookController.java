package com.gizasystems.PoC.controllers;


import com.gizasystems.PoC.entities.Role;
import com.gizasystems.PoC.entities.User;
import com.gizasystems.PoC.repositories.UserRepository;
import com.gizasystems.PoC.services.RateLimiterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {


    private UserRepository userRepository;
    private RateLimiterService rateLimiterService;

    @GetMapping
    public ResponseEntity<String> getBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(authentication);
        User user = userRepository.findByUsername(username).orElseThrow();
        assert user.getRoles() != null;

        int limit = user.getRequestLimit();
        if (!rateLimiterService.isAllowed(username, limit)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }

        if (user.getRoles().contains(Role.ROLE_PREMIUM_USER.toString()) || user.getRoles().contains(Role.ROLE_ADMIN.toString())) {
            return ResponseEntity.ok("Listing all free and premium books");
        } else {
            return ResponseEntity.ok("Listing all free books");
        }
    }
}
