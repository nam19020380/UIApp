package org.example.repository;

import org.example.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
    public Post findByPostId(Integer id);
    public void deleteByPostId(Integer id);
    public List<Post> findByCategory(String category, Pageable pageable);
}
