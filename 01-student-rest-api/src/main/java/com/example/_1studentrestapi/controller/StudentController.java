package com.example._1studentrestapi.controller;

import com.example._1studentrestapi.model.Student;
import com.example._1studentrestapi.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController           // Handles HTTP requests + auto-converts to JSON
@RequestMapping("/api/students") // Base URL for all endpoints
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET /api/students → returns all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET /api/students/1 → returns student with id=1
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // POST /api/students → create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/students/1 → update student with id=1
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // DELETE /api/students/1 → delete student with id=1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
