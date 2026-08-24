package com.example._1studentrestapi;

import com.example._1studentrestapi.model.Student;
import com.example._1studentrestapi.repository.StudentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component  // Spring runs this automatically after startup
public class DataLoader implements CommandLineRunner {

    private final StudentRepository repo;

    public DataLoader(StudentRepository repo) { this.repo = repo; }

    @Override
    public void run(String @NonNull ... args) {
        repo.save(new Student("Aarav Sharma",    "aarav@example.com",  "Computer Science", 20));
        repo.save(new Student("Priya Nair",      "priya@example.com",  "Mechanical",        22));
        repo.save(new Student("Karan Mehta",     "karan@example.com",  "Electronics",       21));
        repo.save(new Student("Divya Reddy",     "divya@example.com",  "Civil",             23));
        System.out.println("✅ Sample students loaded into DB!");
    }
}
