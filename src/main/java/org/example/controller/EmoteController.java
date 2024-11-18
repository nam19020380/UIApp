package org.example.controller;

import org.example.entity.Comment;
import org.example.entity.Emote;
import org.example.payload.request.EmoteRequest;
import org.example.security.UserDetailsImpl;
import org.example.service.CommentService;
import org.example.service.EmoteService;
import org.example.service.StatusService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/v1/emote")
public class EmoteController {
    @Autowired
    EmoteService emoteService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    StatusService statusService;

    @PostMapping
    public ResponseEntity<?> createEmote(@RequestBody EmoteRequest emoteRequest){
        return emoteService.saveEmote(emoteRequest);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmote(@RequestParam Integer id){
        return emoteService.deleteById(id);
    }


}
