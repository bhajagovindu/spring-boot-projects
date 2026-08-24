package com.example._3userauthsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity    // activates Spring Security for this app
@EnableMethodSecurity // enables @PreAuthorize on controller methods
public class SecurityConfig {

    private final JwtAuthFilter             jwtAuthFilter;
    private final CustomUserDetailsService   userDetailsService;

    public SecurityConfig(JwtAuthFilter f, CustomUserDetailsService u) {
        jwtAuthFilter = f; userDetailsService = u;
    }

    // ── Bean 1: BCrypt password encoder ───────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ── Bean 2: Authentication manager ────────────────
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ── Bean 3: Security filter chain — THE main config ─
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                // CSRF not needed for stateless REST APIs
                .csrf(csrf -> csrf.disable())

                // No server sessions — every request must bring a JWT
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ── URL access rules ──────────────────────────
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints — no token needed
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // GET requests on /api/** are public (anyone can read)
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        // Everything else requires a valid JWT token
                        .anyRequest().authenticated()
                )

                // Allow H2 console to render inside an iframe
                .headers(h -> h.frameOptions(f -> f.disable()))

                // Wire our custom UserDetailsService
                .userDetailsService(userDetailsService)

                // Insert our JWT filter BEFORE Spring's default auth filter
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}