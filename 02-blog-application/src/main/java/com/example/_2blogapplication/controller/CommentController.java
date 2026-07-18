package com.example._2blogapplication.controller;

import com.example._2blogapplication.dto.CommentDTO;
import com.example._2blogapplication.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
// ↑ Nested URL — every comment endpoint lives under a post
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/posts/1/comments
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // GET /api/posts/1/comments/3
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long postId,
                                                 @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    // POST /api/posts/1/comments
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
                                                    @Valid @RequestBody CommentDTO dto) {
        return new ResponseEntity<>(commentService.createComment(postId, dto), HttpStatus.CREATED);
    }

    // PUT /api/posts/1/comments/3
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @Valid @RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, dto));
    }

    // DELETE /api/posts/1/comments/3
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
