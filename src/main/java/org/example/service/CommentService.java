package org.example.service;

import org.example.entity.Comment;
import org.example.payload.request.CommentRequest;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface CommentService {
    public void saveComment(Comment comment);

    public ResponseEntity<?> createComment(CommentRequest commentRequest);

    public ResponseEntity<?> editComment(CommentRequest commentRequest);

    public ResponseEntity<?> deleteComment(Integer id);

    public ResponseEntity<?> findByStatusId(Integer statusId);

    public ResponseEntity<?> findByCommentId(Integer commentId);

    public void deleteByParentCommentId(Integer id);

    public void deleteByStatusId(Integer id);

    public List<Comment> findByUserUserId(Integer userId);
    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);

    public Comment findById(Integer commentId);

    public Integer countByStatusStatusId(Integer statusId);

    public Integer countByCommentCommentId(Integer commentId);

    public Integer countByParentCommentId(Integer commentId);

    public Boolean existsByUserUserIdAndCommentId(Integer userId, Integer commentId);
}
