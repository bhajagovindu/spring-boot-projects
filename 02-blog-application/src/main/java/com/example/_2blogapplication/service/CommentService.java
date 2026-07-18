package com.example._2blogapplication.service;

import com.example._2blogapplication.dto.CommentDTO;
import com.example._2blogapplication.exception.ResourceNotFoundException;
import com.example._2blogapplication.model.Comment;
import com.example._2blogapplication.model.Post;
import com.example._2blogapplication.repository.CommentRepository;
import com.example._2blogapplication.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository    = postRepository;
    }

    // ── Get all comments for a post ───────────────────
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        // First validate post exists
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── Get one comment by id (must belong to the post)
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Verify the comment actually belongs to the post
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new RuntimeException("Comment does not belong to this post");
        }
        return mapToDTO(comment);
    }

    // ── Create a comment on a post ────────────────────
    @Transactional
    public CommentDTO createComment(Long postId, CommentDTO dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = new Comment();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        comment.setPost(post);  // link to the parent post

        return mapToDTO(commentRepository.save(comment));
    }

    // ── Update a comment ──────────────────────────────
    @Transactional
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO dto) {
        getCommentById(postId, commentId); // validates both exist and belong together
        Comment comment = commentRepository.findById(commentId).get();
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        comment.setBody(dto.getBody());
        return mapToDTO(commentRepository.save(comment));
    }

    // ── Delete a comment ──────────────────────────────
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        getCommentById(postId, commentId);
        commentRepository.deleteById(commentId);
    }

    private CommentDTO mapToDTO(Comment c) {
        CommentDTO dto = new CommentDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setEmail(c.getEmail());
        dto.setBody(c.getBody());
        return dto;
    }
}