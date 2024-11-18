package org.example.controller;

import org.example.entity.Comment;
import org.example.entity.Status;
import org.example.payload.request.CommentRequest;
import org.example.payload.response.CommentResponse;
import org.example.security.UserDetailsImpl;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ImageService imageService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createComment(@ModelAttribute CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editComment(@ModelAttribute CommentRequest commentRequest) {
        return commentService.editComment(commentRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@RequestParam Integer id) {
        return commentService.deleteComment(id);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> findByStatusId(@RequestParam Integer id) {
        return commentService.findByStatusId(id);
    }

    @GetMapping(value = "/comment")
    public ResponseEntity<?> findByCommentId(@RequestParam Integer id) {
        return commentService.findByCommentId(id);
    }

    @GetMapping(value = "/image/commentId")
    public ResponseEntity<?> getImageByCommentId(@RequestParam Integer id){
        try{
            Comment comment = commentService.findById(id);
            final ByteArrayResource inputStream = imageService.getImage(comment.getImageLink());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
