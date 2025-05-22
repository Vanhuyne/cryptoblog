package com.huy.cryptoblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private UUID id;
    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Content required")
    private String content;

    @Size(max = 1000, message = "Summary cannot exceed 1000 characters")
    private String summary;
    private String coverImageUrl;
    private Boolean isPublished = true;

}
