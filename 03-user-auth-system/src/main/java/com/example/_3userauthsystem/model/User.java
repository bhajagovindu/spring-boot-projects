package com.example._3userauthsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // ALWAYS stored as a BCrypt hash, never plain text

    @Enumerated(EnumType.STRING) // stores "ROLE_USER" string in DB, not a number
    private Role role;

    public User() {}

    public User(String name, String email, String password, Role role) {
        this.name = name; this.email = email;
        this.password = password; this.role = role;
    }

    public Long   getId()               { return id; }
    public String getName()             { return name; }
    public void    setName(String n)     { this.name = n; }
    public String getEmail()            { return email; }
    public void    setEmail(String e)    { this.email = e; }
    public String getPassword()         { return password; }
    public void    setPassword(String p) { this.password = p; }
    public Role   getRole()              { return role; }
    public void    setRole(Role r)        { this.role = r; }
}
