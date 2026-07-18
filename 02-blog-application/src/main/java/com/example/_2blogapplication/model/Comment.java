package com.example._2blogapplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String body;

    // ── Relationship: many Comments belong to ONE Post ─
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    // ↑ This creates the post_id foreign key column in the comments table
    private Post post;

    public Comment(String name, String email, String body, Post post) {
        this.name = name;
        this.email = email;
        this.body = body;
        this.post = post;
    }
}
