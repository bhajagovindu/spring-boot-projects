package com.example._3userauthsystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter = guaranteed to run exactly ONCE per request

    private final JwtUtils                jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtils j, CustomUserDetailsService u) {
        this.jwtUtils = j;
        this.userDetailsService = u;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Pull token from "Authorization: Bearer xxx" header
        String token = extractToken(request);

        // 2. Only proceed if token exists AND signature is valid
        if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {

            // 3. Get the email stored inside the token
            String email = jwtUtils.getEmailFromToken(token);

            // 4. Load the full user (with roles) from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 5. Create an Authentication object containing user + roles
            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. Tell Spring Security: "this request is authenticated"
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 7. Always continue to the next filter / controller
        filterChain.doFilter(request, response);
    }

    // Strips "Bearer " prefix from the Authorization header value
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7); // skip "Bearer " (7 characters)
        }
        return null;
    }
}
