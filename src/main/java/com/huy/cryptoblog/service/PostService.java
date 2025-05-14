package com.huy.cryptoblog.service;

import com.github.slugify.Slugify;
import com.huy.cryptoblog.dto.PostResponse;
import com.huy.cryptoblog.model.Post;
import com.huy.cryptoblog.repository.CategoryRepo;
import com.huy.cryptoblog.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final Slugify slugify;
    private final CategoryRepo categoryRepo;

    // Get all posts
    public List<PostResponse> getAllPosts() {
        return postRepo.findAll().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    // mapping post to post response
    private PostResponse mapToPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getContent(),
                post.getSummary(),
                post.getCoverImageUrl(),
                post.getIsPublished(),
                post.getCategory().getId(),
                post.getCategory().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
