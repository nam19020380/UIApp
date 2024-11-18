package org.example.controller;

import org.example.entity.Post;
import org.example.security.UserDetailsImpl;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody Post post) {
        try {
            postService.createPost(post);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @PutMapping
    public ResponseEntity<?> editQuiz(@RequestBody Post post) {
        try {
            postService.createPost(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping(value = "/postId")
    public ResponseEntity<?> getPost(@RequestParam Integer Id) {
        try {
            return new ResponseEntity<>(postService.getPostById(Id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping(value = "/newPost")
    public ResponseEntity<?> getNewPost(@RequestParam Integer page, @RequestParam Integer perPage) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, perPage, Sort.by("date").descending());
            return new ResponseEntity<>(postService.getNewPost(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping(value = "/newPostByCategory")
    public ResponseEntity<?> getNewPostByCategory(@RequestParam String category
            , @RequestParam Integer page, @RequestParam Integer perPage) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, perPage, Sort.by("date").descending());
            return new ResponseEntity<>(postService.getNewPostByCategory(category, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteQuiz(@RequestBody Integer id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }
}
