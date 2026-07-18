package com.example._2blogapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;


    private String author;

    // ── Relationship: one Post has MANY Comments ──────
    @OneToMany(
            mappedBy = "post",         // refers to the field name in Comment.java
            cascade = CascadeType.ALL,   // delete post → auto-delete its comments
            orphanRemoval = true,       // removing from list also deletes from DB
            fetch = FetchType.LAZY       // don't load comments unless asked
    )
    private List<Comment> comments=new ArrayList<>();

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
