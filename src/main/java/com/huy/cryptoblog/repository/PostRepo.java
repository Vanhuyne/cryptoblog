package com.huy.cryptoblog.repository;

import com.huy.cryptoblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {
//    Post findBySlug(String slug);
//    Post findByTitle(String title);
//    Post findById(Long id);
}
