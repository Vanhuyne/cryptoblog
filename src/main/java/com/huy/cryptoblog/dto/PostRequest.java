package com.huy.cryptoblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @NotBlank(message = "Title required")
    private String title;
    private String slug;

    @NotBlank(message = "Content required")
    private String content;

    @Size(max = 1000, message = "Summary cannot exceed 1000 characters")
    private String summary;
    private String coverImageUrl;
    private Boolean isPublished = true;

    @NotNull(message = "Category ID cannot be null")
    private UUID categoryId;

    // Add any other fields you need for the post request
}
