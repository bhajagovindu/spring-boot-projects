package com.example._2blogapplication.controller;

import com.example._2blogapplication.dto.PostDto;
import com.example._2blogapplication.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPosts(
            @RequestParam(defaultValue = "0")
            int pageNo,

            @RequestParam(defaultValue = "5")
            int pageSize,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    // GET /api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // POST /api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto dto) {
        return new ResponseEntity<>(postService.createPost(dto), HttpStatus.CREATED);
    }

    // PUT /api/posts/1
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
                                              @Valid @RequestBody PostDto dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    // DELETE /api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
