package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.Status;
import org.example.payload.request.StatusRequest;
import org.example.payload.response.StatusResponse;
import org.example.repository.StatusRepository;
import org.example.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService{
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Transactional(rollbackOn = Exception.class)
    public void saveStatus(Status status) {
        statusRepository.save(status);
    }

    public ResponseEntity<?> createStatus(StatusRequest statusRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Status status = new Status();
            status.setUser(userService.findUserById(userDetails.getId()));
            status.setContent(statusRequest.getContent());
            status.setImageLink(imageService.uploadImage(statusRequest.getFile()));
            status.setDate(new java.sql.Date(new java.util.Date().getTime()));
            statusRepository.save(status);
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setId(status.getStatusId());
            statusResponse.setText(status.getContent());
            statusResponse.setOwnerName(status.getUser().getEmail());
            statusResponse.setDate(status.getDate());
            statusResponse.setCommentCount(commentService.countByStatusStatusId(status.getStatusId()));
            statusResponse.setEmoteCount(emoteService.countByStatusStatusId(status.getStatusId()));
            return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> editStatus(StatusRequest statusRequest){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Integer id = statusRequest.getId();
            if(statusRepository.existsByUserUserIdAndStatusId(userDetails.getId(), id)){
                Status status = new Status();
                status.setStatusId(statusRequest.getId());
                status.setUser(userService.findUserById(userDetails.getId()));
                status.setContent(statusRequest.getContent());
                status.setImageLink(imageService.uploadImage(statusRequest.getFile()));
                status.setDate(new java.sql.Date(new java.util.Date().getTime()));
                statusRepository.save(status);
                StatusResponse statusResponse = new StatusResponse();
                statusResponse.setId(status.getStatusId());
                statusResponse.setText(status.getContent());
                statusResponse.setOwnerName(status.getUser().getEmail());
                statusResponse.setDate(status.getDate());
                statusResponse.setCommentCount(commentService.countByStatusStatusId(status.getStatusId()));
                statusResponse.setEmoteCount(emoteService.countByStatusStatusId(status.getStatusId()));
                return new ResponseEntity<>(statusResponse, HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't edit other's statuses");
            }
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Status> findAll(){
        return statusRepository.findAll();
    }

    public ResponseEntity<?> findByUserId(Integer userId){
        try{
            List<StatusResponse> statusResponses = new ArrayList<>();
            List<Status> statuses = statusRepository.findByUserUserId(userId);
            if(statuses.isEmpty()){
                return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
            } else {
                for(Status status : statuses){
                    StatusResponse statusResponse = new StatusResponse();
                    statusResponse.setId(status.getStatusId());
                    statusResponse.setText(status.getContent());
                    statusResponse.setOwnerName(status.getUser().getEmail());
                    statusResponse.setDate(status.getDate());
                    statusResponse.setCommentCount(commentService.countByStatusStatusId(status.getStatusId()));
                    statusResponse.setEmoteCount(emoteService.countByStatusStatusId(status.getStatusId()));
                    statusResponses.add(statusResponse);
                }
            }
            return new ResponseEntity<>(statusResponses, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteById(Integer id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(statusRepository.existsByUserUserIdAndStatusId(userDetails.getId(), id)){
                Status status = statusRepository.findByStatusId(id);
                imageService.deleteImage(status.getImageLink());
                commentService.deleteByStatusId(id);
                emoteService.deleteByStatusId(id);
                statusRepository.deleteByStatusId(id);
                return new ResponseEntity<>("Xoa Status thanh cong", HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest()
                        .body("You can't delete other's statuses");
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Status findByStatusId(Integer id){
        return statusRepository.findByStatusId(id);
    }

    public ResponseEntity<?> findStatus(Integer id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Status status = statusRepository.findByStatusId(id);
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.setId(id);
            statusResponse.setText(status.getContent());
            statusResponse.setOwnerName(userDetails.getUsername());
            statusResponse.setDate(status.getDate());
            statusResponse.setCommentCount(commentService.countByStatusStatusId(id));
            statusResponse.setEmoteCount(emoteService.countByStatusStatusId(id));
            return ResponseEntity.ok()
                    .body(statusResponse);
        }catch (Exception e){
            return new ResponseEntity<>("Sever error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public List<Status> findByUserUserIdAndDateOrderByStatusId(Integer userId, Date date){
        return statusRepository.findByUserUserIdAndDateOrderByStatusId(userId, date);
    }

    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date){
        return statusRepository.countByUserUserIdAndDateGreaterThanEqual(userId, date);
    }

    public List<Status> findByUserUserIdOrderByDate(Integer userId){
        return statusRepository.findByUserUserIdOrderByDate(userId);
    }

    public List<Status> findNewStatus (Pageable pageable){
        return statusRepository.findAll(pageable).getContent();
    }

    public Boolean existsByUserUserIdAndStatusId (Integer userId, Integer statusId){
        return statusRepository.existsByUserUserIdAndStatusId(userId, statusId);
    }
}
