package com.example._3userauthsystem.controller;

import com.example._3userauthsystem.dto.AuthResponse;
import com.example._3userauthsystem.dto.LoginRequest;
import com.example._3userauthsystem.dto.RegisterRequest;
import com.example._3userauthsystem.model.Role;
import com.example._3userauthsystem.model.User;
import com.example._3userauthsystem.repo.UserRepository;
import com.example._3userauthsystem.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository ur, PasswordEncoder pe,
                          AuthenticationManager am, JwtUtils jwt) {
        userRepository = ur; passwordEncoder = pe;
        authManager = am; jwtUtils = jwt;
    }

    // ── POST /auth/register ───────────────────────────
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Email already registered!");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        // Hash the password before saving — NEVER save plain text
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.ROLE_USER);  // all new users get ROLE_USER by default

        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully!");
    }

    // ── POST /auth/login ──────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest req) {

        // AuthenticationManager loads user from DB via CustomUserDetailsService,
        // then BCrypt-compares the provided password with the stored hash.
        // If wrong → throws BadCredentialsException automatically (401)
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(), req.getPassword())
        );

        // Password matched — generate and return the JWT
        String token = jwtUtils.generateToken(auth);
        return ResponseEntity.ok(
                new AuthResponse(token, "Login successful!"));
    }
}
