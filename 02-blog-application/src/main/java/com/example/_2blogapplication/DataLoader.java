package com.example._2blogapplication;

import com.example._2blogapplication.model.Comment;
import com.example._2blogapplication.model.Post;
import com.example._2blogapplication.repository.CommentRepository;
import com.example._2blogapplication.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PostRepository postRepo;
    private final CommentRepository commentRepo;

    public DataLoader(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo    = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public void run(String... args) {
        // Create posts
        Post p1 = postRepo.save(new Post("Getting Started with Spring Boot",
                "Spring Boot makes it easy to create stand-alone, production-grade apps...", "Bhaja"));
        Post p2 = postRepo.save(new Post("Understanding JPA and Hibernate",
                "JPA is a Java specification for accessing, persisting, and managing data...", "Bhaja"));
        Post p3 = postRepo.save(new Post("REST API Best Practices",
                "Building REST APIs with proper status codes and resource naming...", "Govindu"));

        // Create comments linked to posts
        commentRepo.save(new Comment("Ravi Kumar",  "ravi@mail.com",  "Very helpful article!", p1));
        commentRepo.save(new Comment("Priya Nair",  "priya@mail.com", "Clear explanation, thanks.", p1));
        commentRepo.save(new Comment("Arjun Mehta", "arjun@mail.com", "Loved the JPA section!", p2));
        commentRepo.save(new Comment("Deepa Rao",   "deepa@mail.com", "Great REST tips!", p3));

        System.out.println("✅ Blog sample data loaded!");
    }
}
