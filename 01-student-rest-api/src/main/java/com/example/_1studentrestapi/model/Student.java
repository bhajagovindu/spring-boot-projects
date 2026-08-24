package com.example._1studentrestapi.model;

import jakarta.persistence.*;

@Entity                    // Marks this class as a DB table
@Table(name = "students") // Table name in H2
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String department;

    private int age;

    // ── Constructors ──────────────────────────────────
    public Student() {}  // Required by JPA

    public Student(String name, String email, String department, int age) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.age = age;
    }

    // ── Getters & Setters ─────────────────────────────
    public Long getId()              { return id; }
    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail()         { return email; }
    public void setEmail(String email){ this.email = email; }
    public String getDepartment()    { return department; }
    public void setDepartment(String d){ this.department = d; }
    public int    getAge()            { return age; }
    public void   setAge(int age)     { this.age = age; }
}
