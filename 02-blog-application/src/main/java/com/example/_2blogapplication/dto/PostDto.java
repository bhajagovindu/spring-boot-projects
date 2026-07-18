package com.example._2blogapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;   // returned in response, not required in request

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    @Size(min = 10, message = "Content must have at least 10 characters")
    private String content;

    private String author;

    // Comments are included in the response (read-only, not required in request)
    private List<CommentDTO> comments;
}
