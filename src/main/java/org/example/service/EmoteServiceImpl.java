package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.Emote;
import org.example.payload.request.EmoteRequest;
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
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@Service
public class EmoteServiceImpl implements EmoteService{
    @Autowired
    EmoteRepository emoteRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> saveEmote(EmoteRequest emoteRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Emote emote = new Emote();
            if(emoteRequest.getCommentId() == null){
                if(!emoteRepository.existsByUserUserIdAndStatusStatusId(userDetails.getId(), emoteRequest.getStatusId())){
                    emote.setStatus(statusRepository.findByStatusId(emoteRequest.getStatusId()));
                } else {
                    return new ResponseEntity<>("Already liked", HttpStatus.BAD_REQUEST);
                }
            } else {
                if(!emoteRepository.existsByUserUserIdAndCommentCommentId(userDetails.getId(), emoteRequest.getCommentId())){
                    emote.setComment(commentRepository.findByCommentId(emoteRequest.getCommentId()));
                } else {
                    return new ResponseEntity<>("Already liked", HttpStatus.BAD_REQUEST);
                }
            }
            emote.setUser(userRepository.findByUserId(userDetails.getId()));
            emote.setDate(new java.sql.Date(new java.util.Date().getTime()));
            emoteRepository.save(emote);
            return new ResponseEntity<>("Tao emote thanh cong", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Emote> findByStatusId(Integer id){
        return emoteRepository.findByStatusStatusId(id);
    }

    public List<Emote> findByCommentId(Integer id){
        return emoteRepository.findByCommentCommentId(id);
    }

    public void deleteByStatusId(Integer id){
        emoteRepository.deleteByStatusStatusId(id);
    }

    public void deleteByCommentId(Integer id) {
        emoteRepository.deleteByCommentCommentId(id);
    }

    public ResponseEntity<?> deleteById(Integer id) throws ResponseStatusException{
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(emoteRepository.existsByUserUserIdAndEmoteId(userDetails.getId(), id)){
                emoteRepository.deleteByEmoteId(id);
                return new ResponseEntity<>("Xoa emote thanh cong", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You can't delete other's emote", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date){
        return emoteRepository.countByUserUserIdAndDateGreaterThanEqual(userId, date);
    }

    public Integer countByStatusStatusId(Integer statusId){
        return emoteRepository.countByStatusStatusId(statusId);
    }

    public Integer countByCommentCommentId(Integer commentId){
        return emoteRepository.countByCommentCommentId(commentId);
    }

    public Boolean existsByUserUserIdAndEmoteId(Integer userId, Integer emoteId){
        return emoteRepository.existsByUserUserIdAndEmoteId(userId, emoteId);
    }

    public Boolean existsByUserUserIdAndStatusStatusId(Integer userId, Integer statusId){
        return emoteRepository.existsByUserUserIdAndStatusStatusId(userId, statusId);
    }

    public Boolean existsByUserUserIdAndCommentCommentId(Integer userId, Integer commentId){
        return emoteRepository.existsByUserUserIdAndCommentCommentId(userId, commentId);
    }
}
