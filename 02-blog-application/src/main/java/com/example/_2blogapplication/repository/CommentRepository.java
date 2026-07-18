package com.example._2blogapplication.repository;

import com.example._2blogapplication.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Spring auto-generates: SELECT * FROM comments WHERE post_id = ?
    List<Comment> findByPostId(Long postId);
}
