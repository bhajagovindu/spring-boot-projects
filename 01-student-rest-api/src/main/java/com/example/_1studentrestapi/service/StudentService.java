package com.example._1studentrestapi.service;

import com.example._1studentrestapi.model.Student;
import com.example._1studentrestapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    // Constructor injection (best practice over @Autowired on field)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ── Get all students ──────────────────────────────
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ── Get one student by ID ─────────────────────────
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // ── Create a new student ──────────────────────────
    public Student createStudent(Student student) {
        if(studentRepository.findById(student.getId()).isPresent()) {
            throw new RuntimeException("Student already exists with id: " + student.getId());
        }
//        if (studentRepository.existsByEmail(student.getEmail())) {
//            throw new RuntimeException("Email already registered: " + student.getEmail());
//        }
        return studentRepository.save(student);
    }

    // ── Update an existing student ────────────────────
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setDepartment(updatedStudent.getDepartment());
        existing.setAge(updatedStudent.getAge());
        return studentRepository.save(existing);
    }

    // ── Delete a student ──────────────────────────────
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
