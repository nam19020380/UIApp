package org.example.service;

import org.example.entity.Comment;
import org.example.payload.request.CommentRequest;
import org.example.payload.response.CommentResponse;
import org.example.repository.CommentRepository;
import org.example.repository.EmoteRepository;
import org.example.repository.StatusRepository;
import org.example.repository.UserRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    EmoteRepository emoteRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public ResponseEntity<?> createComment(CommentRequest commentRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Comment comment = new Comment();
            comment.setUser(userRepository.findByUserId(userDetails.getId()));
            if(commentRequest.getCommentId() != null){
                comment.setStatus(statusRepository.findByStatusId(commentRequest.getStatusId()));
                comment.setComment(commentRepository.findByCommentId(commentRequest.getCommentId()));
            } else {
                comment.setStatus(statusRepository.findByStatusId(commentRequest.getStatusId()));
            }
            comment.setContent(commentRequest.getText());
            if(commentRequest.getFile() != null){
                comment.setImageLink(imageService.uploadImage(commentRequest.getFile()));
            }
            comment.setDate(new java.sql.Date(new java.util.Date().getTime()));

            comment = commentRepository.save(comment);
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getCommentId());
            commentResponse.setOwnername(userDetails.getUsername());
            commentResponse.setDate(comment.getDate());

            return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    public ResponseEntity<?> editComment(CommentRequest commentRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(commentRepository.existsByUserUserIdAndCommentId(userDetails.getId(), commentRequest.getId())){
                Comment comment = new Comment();
                comment.setCommentId(commentRequest.getId());
                comment.setUser(userRepository.findByUserId(userDetails.getId()));
                if(commentRequest.getCommentId() != null){
                    comment.setComment(commentRepository.findByCommentId(commentRequest.getCommentId()).getComment());
                } else {
                    comment.setStatus(statusRepository.findByStatusId(commentRequest.getStatusId()));
                }
                comment.setContent(commentRequest.getText());
                String old_address = commentRepository.findByCommentId(commentRequest.getId()).getImageLink();
                imageService.deleteImage(old_address);
                comment.setImageLink(imageService.uploadImage(commentRequest.getFile()));
                comment.setDate(new java.sql.Date(new java.util.Date().getTime()));
                comment = commentRepository.save(comment);
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setId(comment.getCommentId());
                commentResponse.setOwnername(userDetails.getUsername());
                commentResponse.setDate(comment.getDate());
                return new ResponseEntity<>(commentResponse, HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't edit other's comment");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    public ResponseEntity<?> deleteComment(Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(commentRepository.existsByUserUserIdAndCommentId(userDetails.getId(), id)){
                String old_address = commentRepository.findByCommentId(id).getImageLink();
                imageService.deleteImage(old_address);
                commentRepository.deleteByCommentCommentId(id);
                List<Comment> comments = commentRepository.findByCommentCommentId(id);
                commentRepository.deleteByCommentId(id);
                commentRepository.deleteByCommentCommentId(id);
                emoteRepository.deleteByCommentCommentId(id);
                comments.stream().forEach(p -> {
                    emoteRepository.deleteByCommentCommentId(p.getCommentId());
                });
                return new ResponseEntity<>("Xoa comment thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't delete other's comment");
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> findByStatusId(Integer statusId){
        try {
            List<Comment> comments = commentRepository.findByStatusStatusId(statusId);
            List<CommentResponse> commentResponses = new ArrayList<>();
            for(Comment comment : comments){
                commentResponses.add(new CommentResponse(comment.getCommentId(),comment.getUser().getUserName(),
                        comment.getContent(), comment.getDate(),emoteRepository.countByCommentCommentId(comment.getCommentId()),
                        commentRepository.countByCommentCommentId(comment.getCommentId())));
            }
            return ResponseEntity.ok()
                    .body(commentResponses);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByCommentId(Integer commentId){
        try {
            List<Comment> comments = commentRepository.findByCommentCommentId(commentId);
            List<CommentResponse> commentResponses = new ArrayList<>();
            for(Comment comment : comments){
                commentResponses.add(new CommentResponse(comment.getCommentId(),comment.getUser().getUserName(),
                        comment.getContent(), comment.getDate(), commentRepository.countByParentCommentId(commentId),
                        emoteRepository.countByCommentCommentId(commentId)));
            }
            return ResponseEntity.ok()
                    .body(commentResponses);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteByParentCommentId(Integer id){
        commentRepository.deleteByCommentCommentId(id);
    }

    public void deleteByStatusId(Integer id){
        commentRepository.deleteByStatusStatusId(id);
    }

    public List<Comment> findByUserUserId(Integer userId){
        return commentRepository.findByUserUserId(userId);
    }
    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date){
        return commentRepository.countByUserUserIdAndDateGreaterThanEqual(userId, date);
    }

    public Comment findById(Integer commentId){
        return commentRepository.findByCommentId(commentId);
    }

    public Integer countByStatusStatusId(Integer statusId){
        return commentRepository.countByStatusStatusId(statusId);
    }

    public Integer countByCommentCommentId(Integer commentId){
        return commentRepository.countByCommentCommentId(commentId);
    }

    public Integer countByParentCommentId(Integer commentId){
        return commentRepository.countByParentCommentId(commentId);
    }

    public Boolean existsByUserUserIdAndCommentId(Integer userId, Integer commentId){
        return commentRepository.existsByUserUserIdAndCommentId(userId, commentId);
    }
}
