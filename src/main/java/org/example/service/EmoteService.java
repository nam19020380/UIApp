package org.example.service;

import org.example.entity.Emote;
import org.example.payload.request.EmoteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

public interface EmoteService {
    public ResponseEntity<?> saveEmote(EmoteRequest emoteRequest);

    public ResponseEntity<?> deleteById(Integer id);

    public List<Emote> findByStatusId(Integer id);

    public List<Emote> findByCommentId(Integer id);

    public void deleteByStatusId(Integer id);

    public void deleteByCommentId(Integer id);

    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);
    public Integer countByStatusStatusId(Integer statusId);

    public Integer countByCommentCommentId(Integer commentId);

    public Boolean existsByUserUserIdAndEmoteId(Integer userId, Integer emoteId);

    public Boolean existsByUserUserIdAndStatusStatusId(Integer userId, Integer statusId);

    public Boolean existsByUserUserIdAndCommentCommentId(Integer userId, Integer commentId);
}
