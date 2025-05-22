package com.huy.cryptoblog.repository;

import com.huy.cryptoblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {
    boolean existsByTitle(String title);
    Optional<Post> findBySlug(String slug);
    List<Post> findByCategoryId(UUID categoryId);
    List<Post> findByIsPublished(Boolean isPublished);
}
