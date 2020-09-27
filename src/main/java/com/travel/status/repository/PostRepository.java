package com.travel.status.repository;

import com.travel.status.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUserId(String userId);
    List<Post> findAllByPrivacyFalse();
}
