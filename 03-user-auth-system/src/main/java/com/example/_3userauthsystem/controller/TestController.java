package com.example._3userauthsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    // No token needed — allowed via SecurityConfig permitAll() on GET /api/**
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Anyone can see this — no token needed!");
    }

    // Any logged-in user (ROLE_USER or ROLE_ADMIN)
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> userEndpoint(Authentication auth) {
        return ResponseEntity.ok(
                "Hello " + auth.getName() + "! You are authenticated.");
    }

    // ONLY ROLE_ADMIN can reach this — others get 403 Forbidden
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Hello Admin! You have full access.");
    }

    // Shows current user's email and assigned roles from the JWT
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> getMe(Authentication auth) {
        return ResponseEntity.ok(
                "Email: " + auth.getName() +
                        " | Roles: " + auth.getAuthorities());
    }
}
