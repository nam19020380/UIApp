package org.example.service;

import org.example.entity.Post;
import org.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getNewPost(Pageable pageable) {
        return postRepository.findAll(pageable).getContent();
    }

    @Override
    public void editPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void increaseViewCount(Integer id) {
        Post post = postRepository.findByPostId(id);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteByPostId(id);
    }

    @Override
    public Post getPostById(Integer id) {
        return postRepository.findByPostId(id);
    }

    @Override
    public List<Post> getNewPostByCategory(String category, Pageable pageable) {
        return postRepository.findByCategory(category, pageable);
    }
}
