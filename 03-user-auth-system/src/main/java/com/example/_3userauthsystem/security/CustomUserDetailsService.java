package com.example._3userauthsystem.security;

import com.example._3userauthsystem.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security calls this automatically with the email
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + email));

        // Convert our Role enum → Spring Security's GrantedAuthority
        var authority = new SimpleGrantedAuthority(user.getRole().name());

        // Return Spring's built-in User object (not our entity)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),    // username = email
                user.getPassword(), // BCrypt hash
                List.of(authority)   // roles list
        );
    }
}
