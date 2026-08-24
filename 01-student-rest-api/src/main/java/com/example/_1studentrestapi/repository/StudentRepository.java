package com.example._1studentrestapi.repository;

import com.example._1studentrestapi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String name);
    boolean existsByEmail(String email);
}
