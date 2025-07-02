package com.huy.cryptoblog.controller;

import com.huy.cryptoblog.dto.PostRequest;
import com.huy.cryptoblog.dto.PostResponse;
import com.huy.cryptoblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostResponse> posts = postService.getPosts(pageable);
        return ResponseEntity.ok(posts);
    }


    @PostMapping("/create/{categoryId}")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest post, @PathVariable UUID categoryId) {
        PostResponse createdPost = postService.createPost(post , categoryId);
        return ResponseEntity.status(201).body(createdPost);
    }

}
