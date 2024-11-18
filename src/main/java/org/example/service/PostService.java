package org.example.service;

import org.example.entity.Post;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {
    public void createPost(Post post);
    public List<Post> getNewPost(Pageable pageable);
    public void editPost(Post post);
    public void increaseViewCount (Integer id);
    public void deletePost(Integer id);
    public Post getPostById(Integer id);
    public List<Post> getNewPostByCategory(String category, Pageable pageable);
}
