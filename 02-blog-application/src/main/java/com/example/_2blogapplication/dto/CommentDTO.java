package com.example._2blogapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Comment body cannot be empty")
    private String body;
}
