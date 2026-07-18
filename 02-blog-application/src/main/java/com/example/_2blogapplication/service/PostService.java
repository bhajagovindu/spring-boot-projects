package com.example._2blogapplication.service;

import com.example._2blogapplication.dto.CommentDTO;
import com.example._2blogapplication.dto.PostDto;
import com.example._2blogapplication.exception.ResourceNotFoundException;
import com.example._2blogapplication.model.Post;
import com.example._2blogapplication.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // ── Get ALL posts with pagination & sorting ───────
    public Map<String, Object> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Build the sort direction
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create pageable object: which page, how many per page, sort order
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // This ONE call does all the SQL LIMIT/OFFSET work automatically!
        Page<Post> page = postRepository.findAll(pageable);

        List<PostDto> content = page.getContent()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        // Return useful pagination metadata alongside the data
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content",       content);
        response.put("pageNo",        page.getNumber());
        response.put("pageSize",      page.getSize());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages",    page.getTotalPages());
        response.put("last",         page.isLast());
        return response;
    }

    // ── Get ONE post by id ────────────────────────────
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    // ── Create a post ─────────────────────────────────
    @Transactional
    public PostDto createPost(PostDto dto) {
        Post post = mapToEntity(dto);
        Post saved = postRepository.save(post);
        return mapToDTO(saved);
    }

    // ── Update a post ─────────────────────────────────
    @Transactional
    public PostDto updatePost(Long id, PostDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
        return mapToDTO(postRepository.save(post));
    }

    // ── Delete a post (cascade deletes its comments too)
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
        // CascadeType.ALL handles deleting comments automatically!
    }

    // ── Entity → DTO converter ────────────────────────
    private PostDto mapToDTO(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());

        List<CommentDTO> commentDTOs = post.getComments()
                .stream()
                .map(c -> {
                    CommentDTO cd = new CommentDTO();
                    cd.setId(c.getId());
                    cd.setName(c.getName());
                    cd.setEmail(c.getEmail());
                    cd.setBody(c.getBody());
                    return cd;
                }).collect(Collectors.toList());
        dto.setComments(commentDTOs);
        return dto;
    }

    // ── DTO → Entity converter ────────────────────────
    private Post mapToEntity(PostDto dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
        return post;
    }
}
