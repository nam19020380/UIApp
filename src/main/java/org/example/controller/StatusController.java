package org.example.controller;

import org.example.entity.Status;
import org.example.payload.request.StatusRequest;
import org.example.payload.response.StatusResponse;
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

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createStatus(@ModelAttribute StatusRequest statusRequest){
        return statusService.createStatus(statusRequest);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllUserStatus(@RequestParam Integer id){
       return statusService.findByUserId(id);
    }

    @GetMapping(value = "/statusId")
    public ResponseEntity<?> getStatusById(@RequestParam Integer id){
        return statusService.findStatus(id);
    }

    @GetMapping(value = "/image/statusId")
    public ResponseEntity<?> getImageByStatusId(@RequestParam Integer id){
        try{
            Status status = statusService.findByStatusId(id);
            final ByteArrayResource inputStream = imageService.getImage(status.getImageLink());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editStatus(@RequestBody StatusRequest statusRequest){
        return statusService.editStatus(statusRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStatus(@RequestParam Integer id){
        return statusService.deleteById(id);
    }
}
