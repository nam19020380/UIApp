package org.example.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.example.entity.Status;
import org.example.payload.response.ReportResponse;
import org.example.payload.response.StatusResponse;
import org.example.security.UserDetailsImpl;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("/api/v1/feature")
public class FeatureController {
    @Autowired
    UserService userService;
    @Autowired
    StatusService statusService;

    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;
    @GetMapping (value = "/newfeed")
    public ResponseEntity<?> getStatusForNewFeed(@RequestParam Integer page,@RequestParam Integer perPage){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Pageable pageable =  PageRequest.of(page, perPage,
                    Sort.by("date").descending());
            List<StatusResponse> statusResponses = new ArrayList<>();
            List<Status> statuses = statusService.findNewStatus(pageable);
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
            return new ResponseEntity<>(statusResponses, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
