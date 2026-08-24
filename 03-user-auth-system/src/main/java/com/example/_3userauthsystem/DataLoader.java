package com.example._3userauthsystem;

import com.example._3userauthsystem.model.Role;
import com.example._3userauthsystem.model.User;
import com.example._3userauthsystem.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository r, PasswordEncoder e) {
        userRepo = r; encoder = e;
    }

    @Override
    public void run(String... args) {
        if (!userRepo.existsByEmail("admin@blog.com")) {
            userRepo.save(new User(
                    "Admin",
                    "admin@blog.com",
                    encoder.encode("admin123"),  // BCrypt hashed before saving
                    Role.ROLE_ADMIN
            ));
            System.out.println("✅ Admin created — email: admin@blog.com | pass: admin123");
        }
    }
}