package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.Emote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
public interface EmoteRepository extends JpaRepository<Emote, String> {
    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);
    public List<Emote> findByCommentCommentId(Integer commentId);
    public List<Emote> findByStatusStatusId(Integer statusId);
    public void deleteByCommentCommentId(Integer commentId);
    public void deleteByStatusStatusId(Integer statusId);

    public Integer countByStatusStatusId(Integer statusId);

    public Integer countByCommentCommentId(Integer commentId);

    public void deleteByEmoteId(Integer emoteId);

    public Boolean existsByUserUserIdAndEmoteId(Integer userId, Integer emoteId);
    public Boolean existsByUserUserIdAndStatusStatusId(Integer userId, Integer statusId);

    public Boolean existsByUserUserIdAndCommentCommentId(Integer userId, Integer commentId);
}
