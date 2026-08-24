package com.example._3userauthsystem.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")       // reads value from application.properties
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    // Build a cryptographic signing key from the secret string
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ── Called after successful login: create a JWT ───
    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())              // stores email in token payload
                .setIssuedAt(new Date())                     // creation time
                .setExpiration(new Date(
                        System.currentTimeMillis() + jwtExpiration)) // expiry time
                .signWith(getSigningKey())                   // signs with secret key
                .compact();                                  // builds the final JWT string
    }

    // ── Extract email from a token ────────────────────
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // returns the email we stored as subject
    }

    // ── Check if token is valid (signature ok + not expired) ─
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // throws if invalid or expired
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }
}
