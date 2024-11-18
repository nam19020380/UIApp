package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, String> {
    public Comment findByCommentId(Integer commentId);
    public List<Comment> findByUserUserId(Integer userId);
    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);
    public List<Comment> findByCommentCommentId(Integer commentId);
    public List<Comment> findByStatusStatusId(Integer statusId);
    public void deleteByCommentId(Integer commentId);
    public void deleteByCommentCommentId(Integer commentId);
    public void deleteByStatusStatusId(Integer statusId);
    public Integer countByStatusStatusId(Integer statusId);
    public Integer countByCommentCommentId(Integer commentId);
    @Query(value = "SELECT Count(*)\n" +
            "FROM COMMENT AS c \n" +
            "WHERE c.parent_comment_id = ?1", nativeQuery = true)
    public Integer countByParentCommentId(Integer commentId);

    public Boolean existsByUserUserIdAndCommentId(Integer userId, Integer commentId);
}
