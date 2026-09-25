package com.example._4ecommerceapi.service;

import com.example._4ecommerceapi.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    // Allowed image types
    private static final List<String> ALLOWED_TYPES =
            List.of("image/jpeg", "image/png", "image/webp");

    public String saveFile(MultipartFile file) {
        // Step 1: Validate file is not empty
        if (file.isEmpty()) {
            throw new BadRequestException("Please select a file to upload");
        }

        // Step 2: Validate file type
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new BadRequestException(
                    "Only JPEG, PNG and WEBP images are allowed");
        }

        try {
            // Step 3: Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Step 4: Generate unique filename (UUID + original extension)
            String originalName  = file.getOriginalFilename();
            String extension     = originalName.substring(originalName.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            // e.g. "a4f2c1d3-9b8e-4f2a-bc12-1234abcd5678.jpg"

            // Step 5: Save the file bytes to disk
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            // Step 6: Return the relative path to store in DB
            return uploadDir + "/" + uniqueFilename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }

    public void deleteFile(String filePath) {
        if (filePath != null) {
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                System.out.println("Could not delete file: " + filePath);
            }
        }
    }
}