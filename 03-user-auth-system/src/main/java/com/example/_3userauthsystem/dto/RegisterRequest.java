package com.example._3userauthsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank @Email(message = "Valid email required")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public String getName()               { return name; }
    public void    setName(String n)      { this.name = n; }
    public String getEmail()              { return email; }
    public void    setEmail(String e)     { this.email = e; }
    public String getPassword()           { return password; }
    public void    setPassword(String p)  { this.password = p; }
}
