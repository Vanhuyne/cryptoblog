package com.huy.cryptoblog.service;

import com.github.slugify.Slugify;
import com.huy.cryptoblog.dto.PostRequest;
import com.huy.cryptoblog.dto.PostResponse;
import com.huy.cryptoblog.exception.ResourceNotFoundException;
import com.huy.cryptoblog.model.Category;
import com.huy.cryptoblog.model.Post;
import com.huy.cryptoblog.repository.CategoryRepo;
import com.huy.cryptoblog.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepo postRepo;
    private final Slugify slugify;
    private final CategoryRepo categoryRepo;

    // Get all posts
    public List<PostResponse> getAllPosts() {
        logger.info("Fetching all posts");
        return postRepo.findAll().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    // Get posts with pagination
    public Page<PostResponse> getPosts(Pageable pageable) {

        Page<Post> pagePosts =  postRepo.findAll(pageable);
        logger.info("Fetching posts with pagination: page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());

        return pagePosts.map(this::mapToPostResponse);
    }
    // create post
    public PostResponse createPost(PostRequest postRequest, UUID categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // check if post slug is unique
        if (postRepo.existsByTitle(postRequest.getTitle())) {
            throw new ResourceNotFoundException("Post with this title already exists");
        }
        Post post = mapToPost(postRequest, category);
        Post savedPost = postRepo.save(post);
        logger.info("Post created with ID: {}", savedPost.getId());
        return mapToPostResponse(savedPost);

    }

    // update post
    public PostResponse updatePost(PostRequest postRequest, UUID postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setTitle(postRequest.getTitle());
        post.setSlug(slugify.slugify(postRequest.getTitle()));
        post.setContent(postRequest.getContent());
        post.setSummary(postRequest.getSummary());
        post.setCoverImageUrl(postRequest.getCoverImageUrl());
        post.setIsPublished(postRequest.getIsPublished());
        Post updatedPost = postRepo.save(post);

        logger.info("Post updated with ID: {}", updatedPost.getId());
        return mapToPostResponse(updatedPost);
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

    // mapping post request to post
    private Post mapToPost(PostRequest postRequest, Category category) {
        return new Post(
                postRequest.getId(),
                postRequest.getTitle(),
                slugify.slugify(postRequest.getTitle()),
                postRequest.getContent(),
                postRequest.getSummary(),
                postRequest.getCoverImageUrl(),
                postRequest.getIsPublished(),
                category,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
